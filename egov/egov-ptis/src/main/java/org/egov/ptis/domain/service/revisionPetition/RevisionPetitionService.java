package org.egov.ptis.domain.service.revisionPetition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.egov.commons.EgwStatus;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.DesignationService;
import org.egov.eis.service.EisCommonService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.messaging.MessagingService;
import org.egov.infra.search.elastic.entity.ApplicationIndex;
import org.egov.infra.search.elastic.entity.ApplicationIndexBuilder;
import org.egov.infra.search.elastic.service.ApplicationIndexService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.infstr.services.PersistenceService;
import org.egov.infstr.workflow.WorkFlowMatrix;
import org.egov.pims.commons.Designation;
import org.egov.pims.commons.Position;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.property.PropertyStatusDAO;
import org.egov.ptis.domain.entity.objection.RevisionPetition;
import org.egov.ptis.domain.service.property.SMSEmailService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class RevisionPetitionService extends PersistenceService<RevisionPetition, Long> {
    private static final Logger LOGGER = Logger.getLogger(RevisionPetitionService.class);
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private PropertyStatusDAO propertyStatusDAO;
    @Autowired
    DesignationService designationService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    protected AssignmentService assignmentService;
    @Autowired
    private EgwStatusHibernateDAO egwStatusDAO;
    @Autowired
    protected SimpleWorkflowService<RevisionPetition> revisionPetitionWorkFlowService;
    @Autowired
    private EisCommonService eisCommonService;
    @Autowired
    private ApplicationIndexService applicationIndexService;
    private static final String REVISION_PETITION_CREATED = "CREATED";

    @Autowired
    private MessagingService messagingService;
    private SMSEmailService sMSEmailService;

    /**
     * Create revision petition
     * 
     * @param objection
     * @return
     */
    @Transactional
    public RevisionPetition createRevisionPetition(RevisionPetition objection) {
        if (objection.getId() == null)
            objection = persist(objection);
        else
            objection = merge(objection);

        return objection;

    }

    /**
     * Api to save revision petition using rest api's.
     * 
     * @param objection
     * @return
     */
    @Transactional
    public RevisionPetition createRevisionPetitionForRest(RevisionPetition objection) {
        Position position = null;
        WorkFlowMatrix wfmatrix = null;
        User user = null;
        if (objection.getId() == null) {
            if (objection.getObjectionNumber() == null)
                objection.setObjectionNumber(applicationNumberGenerator.generate());
            objection.getBasicProperty().setStatus(
                    propertyStatusDAO.getPropertyStatusByCode(PropertyTaxConstants.STATUS_OBJECTED_STR));
            objection.getBasicProperty().setUnderWorkflow(Boolean.TRUE);
            if (objection.getState() == null) {
                wfmatrix = revisionPetitionWorkFlowService.getWfMatrix(objection.getStateType(), null, null, null,
                        PropertyTaxConstants.REVISIONPETITION_CREATED, null);
                // Get the default revenue cleark from admin boundary.
                final Designation desig = designationService
                        .getDesignationByName(PropertyTaxConstants.REVENUE_CLERK_DESGN);
                List<Assignment> assignment = assignmentService.findByDesignationAndBoundary(desig.getId(), objection
                        .getBasicProperty().getPropertyID().getZone().getId());
                if (assignment.size() > 0)
                    position = assignment.get(0).getPosition();
                else {
                    assignment = assignmentService
                            .findPrimaryAssignmentForDesignationName(PropertyTaxConstants.REVENUE_CLERK_DESGN);
                    if (assignment.size() > 0)
                        position = assignment.get(0).getPosition();
                }

                updateRevisionPetitionStatus(wfmatrix, objection, null);

                if (position != null)
                    user = eisCommonService.getUserForPosition(position.getId(), new Date());

                objection.start().withNextAction(wfmatrix.getPendingActions())
                .withStateValue(wfmatrix.getCurrentState()).withOwner(position)
                .withSenderName(user != null && user.getName() != null ? user.getName() : "").withOwner(user)
                .withComments("");
            }

            applyAuditing(objection.getState());
            objection = persist(objection);
            updateIndex(objection);

            sendEmailandSms(objection, REVISION_PETITION_CREATED);
        } else
            objection = merge(objection);

        return objection;

    }

    /**
     * Update elastic search index
     * 
     * @param objection
     */
    private void updateIndex(final RevisionPetition objection) {
        final ApplicationIndex applicationIndex = applicationIndexService.findByApplicationNumber(objection
                .getObjectionNumber());
        final User user = securityUtils.getCurrentUser();
        final String url = "/ptis/view/viewProperty-viewForm.action?applicationNo=" + objection.getObjectionNumber();
        if (null == applicationIndex) {
            final ApplicationIndexBuilder applicationIndexBuilder = new ApplicationIndexBuilder(
                    PropertyTaxConstants.PTMODULENAME, objection.getObjectionNumber(),
                    objection.getCreatedDate() != null ? objection.getCreatedDate() : new Date(),
                    PropertyTaxConstants.APPLICATION_TYPE_REVISION_PETITION, objection.getBasicProperty()
                            .getFullOwnerName(), objection.getState().getValue(), url, objection.getBasicProperty()
                            .getAddress().toString(),(user.getUsername() + "::"+ user.getName()));
            applicationIndexService.createApplicationIndex(applicationIndexBuilder.build());
        } else {
            applicationIndex.setStatus(objection.getState().getValue());
            applicationIndexService.updateApplicationIndex(applicationIndex);
        }
    }

    /**
     * @param wfmatrix
     * @param objection
     * @param status
     */
    private void updateRevisionPetitionStatus(final WorkFlowMatrix wfmatrix, final RevisionPetition objection,
            final String status) {

        EgwStatus egwStatus = null;
        if (status != null && !"".equals(status))
            egwStatus = egwStatusDAO.getStatusByModuleAndCode(PropertyTaxConstants.OBJECTION_MODULE, status);

        else if (wfmatrix != null && wfmatrix.getNextStatus() != null && objection != null)
            egwStatus = egwStatusDAO.getStatusByModuleAndCode(PropertyTaxConstants.OBJECTION_MODULE,
                    wfmatrix.getNextStatus());
        if (egwStatus != null)
            objection.setEgwStatus(egwStatus);

    }

    /**
     * Api to update revision petition.
     * 
     * @param objection
     * @return
     */

    @Transactional
    public RevisionPetition updateRevisionPetition(RevisionPetition objection) {
        if (objection.getId() == null)
            objection = persist(objection);
        else
            objection = update(objection);

        return objection;

    }

    /**
     * Get revision petition by application number
     * 
     * @param applicationNumber
     * @return
     */
    public RevisionPetition getRevisionPetitionByApplicationNumber(final String applicationNumber) {
        RevisionPetition revPetitionObject = null;
        final Criteria appCriteria = getSession().createCriteria(RevisionPetition.class, "revPetiton");
        appCriteria.add(Restrictions.eq("revPetiton.objectionNumber", applicationNumber));
        revPetitionObject = (RevisionPetition) appCriteria.uniqueResult();

        return revPetitionObject;
    }

    /**
     * Api to send EMAIL and SMS.
     * 
     * @param objection
     * @param applicationType
     */
    public void sendEmailandSms(final RevisionPetition objection, final String applicationType) {

        if (objection != null) {
            final User user = objection.getBasicProperty().getPrimaryOwner();
            final String mobileNumber = user.getMobileNumber();
            final String emailid = user.getEmailId();
            final String applicantName = user.getName();
            final List<String> args = new ArrayList<String>();
            args.add(applicantName);
            String smsMsg = "";
            String emailSubject = "";
            String emailBody = "";

            if (applicationType != null && applicationType.equalsIgnoreCase(REVISION_PETITION_CREATED)) {

                args.add(objection.getObjectionNumber());
                if (mobileNumber != null)
                    smsMsg = "Revision petition created. Use " + objection.getObjectionNumber()
                            + " for future reference";
                if (emailid != null) {
                    emailSubject = "Revision petition created.";
                    emailBody = "Revision petition created. Use " + objection.getObjectionNumber()
                            + " for future reference";
                }
            }
            if (mobileNumber != null && !smsMsg.equals(""))
                messagingService.sendSMS(mobileNumber, smsMsg);
            if (emailid != null && !emailBody.equals(""))
                messagingService.sendEmail(emailid, emailSubject, emailBody);
        }
    }

    public SMSEmailService getsMSEmailService() {
        return sMSEmailService;
    }

    public void setsMSEmailService(final SMSEmailService sMSEmailService) {
        this.sMSEmailService = sMSEmailService;
    }

}
