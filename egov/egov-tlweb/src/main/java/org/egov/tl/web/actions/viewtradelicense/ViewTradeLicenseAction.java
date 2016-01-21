/*
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
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.tl.web.actions.viewtradelicense;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.EgovThreadLocals;
import org.egov.infra.web.struts.annotation.ValidationErrorPageExt;
import org.egov.tl.entity.LicenseStatus;
import org.egov.tl.entity.TradeLicense;
import org.egov.tl.entity.WorkflowBean;
import org.egov.tl.service.AbstractLicenseService;
import org.egov.tl.service.TradeLicenseService;
import org.egov.tl.utils.Constants;
import org.egov.tl.web.actions.BaseLicenseAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ParentPackage("egov")
@Results({
        @Result(name = "auditReport", type = "redirectAction", location = "auditReport", params = {"moduleName", "TL",
                "namespace",
                "/egi/auditing", "method", "searchForm", "actionName", "auditReport", "prependServletContext", "false"}),
        @Result(name = "duplicate", location = "viewTradeLicense-duplicate.jsp"),
        @Result(name = Constants.CNCCERTIFICATE, location = "viewTradeLicense-" + Constants.CNCCERTIFICATE + ".jsp"),
        @Result(name = Constants.PFACERTIFICATE, location = "viewTradeLicense-" + Constants.PFACERTIFICATE + ".jsp")
})
public class ViewTradeLicenseAction extends BaseLicenseAction implements ServletRequestAware {
    private static final long serialVersionUID = 1L;

    private final String CITIZENUSER = "citizenUser";
    protected TradeLicense tradeLicense = new TradeLicense();
    private String rejectreason;
    private HttpSession session;
    private HttpServletRequest requestObj;
    private Long userId;

    @Autowired
    private TradeLicenseService tradeLicenseService;

    /**
     * @return the rejectreason
     */
    public String getRejectreason() {
        return this.rejectreason;
    }

    /**
     * @param rejectreason the rejectreason to set
     */
    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason;
    }

    @Override
    public TradeLicense getModel() {
        return this.tradeLicense;

    }

    @Override
    @Action(value = "/viewtradelicense/viewTradeLicense-showForApproval")
    public String showForApproval() {
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return super.showForApproval();
    }

    @Action(value = "/viewtradelicense/viewTradeLicense-view")
    public String view() {
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return Constants.VIEW;
    }

    @Action(value = "/viewtradelicense/viewTradeLicense-viewCitizen")
    public String viewCitizen() {
        this.session = this.requestObj.getSession();
        User user = this.userService.getUserByUsername(this.CITIZENUSER);
        this.userId = user.getId();
        EgovThreadLocals.setUserId(this.userId);
        this.session.setAttribute("com.egov.user.LoginUserName", user.getName());
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return Constants.VIEW;
    }

    @Action(value = "/viewtradelicense/viewTradeLicense-generateCertificate")
    public String generateCertificate() {
        String certificate = Constants.CNCCERTIFICATE;
        this.setLicenseIdIfServletRedirect();
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        /*
         * if (this.documentManagerService.getDocumentObject(this.tradeLicense.getApplicationNumber(), "egtradelicense") == null)
         * { ViewTradeLicenseAction.LOGGER.debug("Creating Certificate object for DMS"); final Notice notice = new Notice();
         * notice.setDocumentNumber(this.tradeLicense.getApplicationNumber());
         * notice.setDomainName(EGOVThreadLocals.getDomainName()); notice.setModuleName("egtradelicense");
         * notice.setNoticeType(this.license().getClass().getSimpleName() + "-Certificate"); notice.setNoticeDate(new Date());
         * this.request.put("noticeObject", notice); }
         */
        this.tradeLicense.setIsCertificateGenerated(true);
        if (this.tradeLicense.getFeeTypeStr() != null && this.tradeLicense.getFeeTypeStr().equalsIgnoreCase(Constants.PFA))
            certificate = Constants.PFACERTIFICATE;
        else if (this.tradeLicense.getFeeTypeStr() != null && this.tradeLicense.getFeeTypeStr().equalsIgnoreCase(Constants.CNC))
            certificate = Constants.CNCCERTIFICATE;
        else
            certificate = Constants.CNCCERTIFICATE;
        return certificate;
    }

    public String generateNoc() {
        this.setLicenseIdIfServletRedirect();
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.tradeLicense.getId());
        return "noc";
    }

    @SuppressWarnings("unchecked")
    public String createNoc() {
        this.setLicenseIdIfServletRedirect();
        TradeLicense modifiedTL = this.tradeLicense;
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        this.tradeLicense.setSandBuckets(modifiedTL.getSandBuckets());
        this.tradeLicense.setWaterBuckets(modifiedTL.getWaterBuckets());
        this.tradeLicense.setDcpExtinguisher(modifiedTL.getDcpExtinguisher());
        String runningNumber = this.tradeLicenseService.getNextRunningLicenseNumber(Constants.TL_PROVISIONAL_NOC_NUMBER);
        this.tradeLicense.generateNocNumber(runningNumber);
        // this.service().endWorkFlowForLicense(tradeLicense);
        LicenseStatus activeStatus = (LicenseStatus) this.persistenceService
                .find("from org.egov.tl.entity.LicenseStatus where code='ACT'");
        this.tradeLicense.setStatus(activeStatus);
        this.tradeLicenseService.licensePersitenceService().update(this.tradeLicense);
        return "createnoc";
    }

    public String duplicateNoc() {
        return "duplicatenoc";
    }

    public String generateDuplicateNoc() {
        this.setLicenseIdIfServletRedirect();
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return "createnoc";
    }

    private void setLicenseIdIfServletRedirect() {
        if (this.tradeLicense.getId() == null)
            if (this.getSession().get("model.id") != null) {
                tradeLicense.setId(Long.valueOf((Long) getSession().get("model.id")));
                getSession().remove("model.id");
            }
    }

    @SkipValidation
    @Action(value = "/viewtradelicense/viewTradeLicense-generateRejCertificate")
    public String generateRejCertificate() {
        this.setLicenseIdIfServletRedirect();
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return "rejCertificate";
    }

    @SkipValidation
    @Action(value = "/viewtradelicense/viewTradeLicense-certificateForRej")
    public String certificateForRej() {
        this.getSession().get("model.id");
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return "certificateForRej";
    }

    @Action(value = "/viewtradelicense/viewTradeLicense-duplicateCertificate")
    public String duplicateCertificate() {
        return "duplicate";
    }

    @Override
    protected TradeLicense license() {
        return this.tradeLicense;
    }

    @Override
    @SkipValidation
    @ValidationErrorPageExt(action = "approve", makeCall = true, toMethod = "setupWorkflowDetails")
    public String approve() {
        Long userId = this.securityUtils.getCurrentUser().getId();
        if (userId != null)
            this.setRoleName(this.licenseUtils.getRolesForUserId(userId));
        return super.approve();
    }

    @Override
    @SkipValidation
    @ValidationErrorPageExt(
            action = "approveRenew", makeCall = true, toMethod = "setupWorkflowDetails")
    public String approveRenew() {
        Long userId = this.securityUtils.getCurrentUser().getId();
        if (userId != null)
            this.setRoleName(this.licenseUtils.getRolesForUserId(userId));
        this.tradeLicense = this.tradeLicenseService.getLicenseById(this.license().getId());
        return super.approveRenew();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractLicenseService licenseService() {
        return this.tradeLicenseService;
    }

    public WorkflowBean getWorkflowBean() {
        return this.workflowBean;
    }

    public void setWorkflowBean(WorkflowBean workflowBean) {
        this.workflowBean = workflowBean;
    }

    @Override
    @SkipValidation
    public void setServletRequest(HttpServletRequest arg0) {
        this.requestObj = arg0;
    }

}
