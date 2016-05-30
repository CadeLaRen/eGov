/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 * 	1) All versions of this program, verbatim or modified must carry this
 * 	   Legal Notice.
 *
 * 	2) Any misrepresentation of the origin of the material is prohibited. It
 * 	   is required that all modified versions of this material be marked in
 * 	   reasonable ways as different from the original version.
 *
 * 	3) This license does not grant any rights to any user of the program
 * 	   with regards to rights under trademark law for use of the trade names
 * 	   or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org
 ******************************************************************************/
package org.egov.stms.transactions.workflow;

import java.util.Date;

import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.EisCommonService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.domain.service.property.PropertyExternalService;
import org.egov.stms.masters.entity.enums.SewerageConnectionStatus;
import org.egov.stms.transactions.entity.SewerageApplicationDetails;
import org.egov.stms.utils.SewerageTaxUtils;
import org.egov.stms.utils.constants.SewerageTaxConstants;
import org.elasticsearch.common.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class ApplicationCommonWorkflow.
 */
public abstract class ApplicationWorkflowCustomImpl implements ApplicationWorkflowCustom {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationWorkflowCustomImpl.class);

    @Autowired
    private EisCommonService eisCommonService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    private SewerageTaxUtils sewerageTaxUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleWorkflowService<SewerageApplicationDetails> sewerageApplicationWorkflowService;

    @Autowired
    public ApplicationWorkflowCustomImpl() {

    }

    @Override
    public void createCommonWorkflowTransition(final SewerageApplicationDetails sewerageApplicationDetails,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction) {
        if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        User currentUser = null;
        final Assignment userAssignment = assignmentService.getPrimaryAssignmentForUser(user.getId());
        Position pos = null;
        Assignment wfInitiator = null;
        String currState = "";
        String natureOfwork = SewerageTaxConstants.WORKFLOWTYPE_DISPLAYNAME + "::"
                + sewerageApplicationDetails.getApplicationType().getName();

        if (null != sewerageApplicationDetails.getId()) {
            currentUser = userService.getUserById(sewerageApplicationDetails.getCreatedBy().getId());
            if (currentUser != null && sewerageApplicationDetails.getConnection().getLegacy()) {
                for (final Role userrole : currentUser.getRoles())
                    if (userrole.getName().equals(SewerageTaxConstants.ROLE_SUPERUSER)) {
                        Position positionuser = sewerageTaxUtils.getZonalLevelClerkForLoggedInUser(
                                sewerageApplicationDetails.getConnection().getConnectionDetail().getPropertyIdentifier());
                        if (positionuser != null)
                            wfInitiator = assignmentService.getPrimaryAssignmentForPositionAndDate(positionuser.getId(),
                                    new Date());
                        break;
                    }
            } else {
                wfInitiator = assignmentService.getPrimaryAssignmentForUser(sewerageApplicationDetails.getCreatedBy().getId());
            }
        }
        if (SewerageTaxConstants.WFLOW_ACTION_STEP_REJECT.equalsIgnoreCase(workFlowAction)) {
            if (wfInitiator.equals(userAssignment)) {
                sewerageApplicationDetails.getConnection().setStatus(SewerageConnectionStatus.INACTIVE);

                sewerageApplicationDetails.setStatus(sewerageTaxUtils.getStatusByCodeAndModuleType(
                        SewerageTaxConstants.APPLICATION_STATUS_CANCELLED, SewerageTaxConstants.MODULETYPE));

                sewerageApplicationDetails.transition(true).end().withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvalComent).withDateInfo(currentDate.toDate()).withNatureOfTask(natureOfwork);
            } else {
                final String stateValue = SewerageTaxConstants.WF_STATE_REJECTED;
                sewerageApplicationDetails.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvalComent)
                        .withStateValue(stateValue).withDateInfo(currentDate.toDate())
                        .withOwner(wfInitiator.getPosition()).withNextAction("Application Rejected")
                        .withNatureOfTask(natureOfwork);
            }
        } else {
            if (null != approvalPosition && approvalPosition != -1 && !approvalPosition.equals(Long.valueOf(0)))
                pos = positionMasterService.getPositionById(approvalPosition);
            
            WorkFlowMatrix wfmatrix = null;
            
            if (null == sewerageApplicationDetails.getState()) {
                if (sewerageTaxUtils.isInspectionFeeCollectionRequired()) {
                    wfmatrix = sewerageApplicationWorkflowService.getWfMatrix(
                            sewerageApplicationDetails.getStateType(), null, null, additionalRule, currState,
                            SewerageTaxConstants.WF_INSPECTIONFEE_COLLECTION);
                } else {
                    wfmatrix = sewerageApplicationWorkflowService.getWfMatrix(
                            sewerageApplicationDetails.getStateType(), null, null, additionalRule, currState, null);
                }
 
                sewerageApplicationDetails.transition().start()
                        .withSenderName(user.getUsername() + "::" + user.getName()).withComments(approvalComent)
                        .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                        .withNextAction(wfmatrix.getNextAction()).withNatureOfTask(natureOfwork);
                
            } else if (SewerageTaxConstants.WF_STATE_CONNECTION_EXECUTION_BUTTON.equalsIgnoreCase(workFlowAction)) {
                wfmatrix = sewerageApplicationWorkflowService.getWfMatrix(sewerageApplicationDetails.getStateType(),
                        null, null, additionalRule, sewerageApplicationDetails.getCurrentState().getValue(), null);
                final AssessmentDetails assessmentDetailsFullFlag = sewerageTaxUtils.getAssessmentDetailsForFlag(
                        sewerageApplicationDetails.getConnection().getConnectionDetail().getPropertyIdentifier(),
                        PropertyExternalService.FLAG_FULL_DETAILS);
                sewerageApplicationDetails.setStatus(sewerageTaxUtils.getStatusByCodeAndModuleType(
                        SewerageTaxConstants.APPLICATION_STATUS_SANCTIONED, SewerageTaxConstants.MODULETYPE));
                // sewerageApplicationDetailsService.updateIndexes(sewerageApplicationDetails);
                if (wfmatrix.getNextAction().equalsIgnoreCase("END"))
                    sewerageApplicationDetails.transition(true).end()
                            .withSenderName(user.getUsername() + "::" + user.getName()).withComments(approvalComent)
                            .withDateInfo(currentDate.toDate()).withNatureOfTask(natureOfwork);
            } else if (null != approvalComent && "Receipt Cancelled".equalsIgnoreCase(approvalComent)) {
                wfmatrix = sewerageApplicationWorkflowService.getWfMatrix(sewerageApplicationDetails.getStateType(),
                        null, null, additionalRule, SewerageTaxConstants.WF_STATE_ASSISTANT_APPROVED, null);
                sewerageApplicationDetails.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvalComent).withStateValue(wfmatrix.getNextState())
                        .withDateInfo(currentDate.toDate()).withOwner(pos).withNextAction(wfmatrix.getNextAction())
                        .withNatureOfTask(natureOfwork);
            } else {
                wfmatrix = sewerageApplicationWorkflowService.getWfMatrix(sewerageApplicationDetails.getStateType(),
                        null, null, additionalRule, sewerageApplicationDetails.getCurrentState().getValue(), null);
                sewerageApplicationDetails.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvalComent).withStateValue(wfmatrix.getNextState())
                        .withDateInfo(currentDate.toDate()).withOwner(pos).withNextAction(wfmatrix.getNextAction())
                        .withNatureOfTask(natureOfwork);
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed  ...");
    }
}
