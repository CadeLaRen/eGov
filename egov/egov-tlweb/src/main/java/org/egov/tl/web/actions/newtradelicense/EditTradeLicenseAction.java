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
package org.egov.tl.web.actions.newtradelicense;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.web.struts.annotation.ValidationErrorPageExt;
import org.egov.tl.entity.FeeMatrixDetail;
import org.egov.tl.entity.License;
import org.egov.tl.entity.LicenseAppType;
import org.egov.tl.entity.LicenseDocumentType;
import org.egov.tl.entity.Licensee;
import org.egov.tl.entity.MotorDetails;
import org.egov.tl.entity.TradeLicense;
import org.egov.tl.entity.WorkflowBean;
import org.egov.tl.service.AbstractLicenseService;
import org.egov.tl.service.FeeMatrixService;
import org.egov.tl.service.TradeLicenseService;
import org.egov.tl.service.masters.LicenseCategoryService;
import org.egov.tl.service.masters.LicenseSubCategoryService;
import org.egov.tl.service.masters.UnitOfMeasurementService;
import org.egov.tl.utils.Constants;
import org.egov.tl.web.actions.BaseLicenseAction;
import org.egov.tl.web.actions.domain.CommonTradeLicenseAjaxAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.egov.tl.utils.Constants.LOCALITY;
import static org.egov.tl.utils.Constants.LOCATION_HIERARCHY_TYPE;
import static org.egov.tl.utils.Constants.TRANSACTIONTYPE_CREATE_LICENSE;

@ParentPackage("egov")
@Results({
        @Result(name = Constants.EDIT, location = "editTradeLicense-" + Constants.EDIT + ".jsp"),
        @Result(name = Constants.NEW, location = "newTradeLicense-" + Constants.NEW + ".jsp"),
        @Result(name = Constants.MESSAGE, location = "editTradeLicense-" + Constants.MESSAGE + ".jsp")
})
public class EditTradeLicenseAction extends BaseLicenseAction {
    private static final long serialVersionUID = 1L;
    /* to log errors and debugging information */
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private TradeLicense tradeLicense = new TradeLicense();
    private boolean isOldLicense;
    private List<LicenseDocumentType> documentTypes = new ArrayList<>();
    private String mode;
    private Map<String, String> ownerShipTypeMap;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<MotorDetails> installedMotorList = new ArrayList<MotorDetails>();
    private Long id;
    @Autowired
    @Qualifier("tradeLicenseService")
    private TradeLicenseService tradeLicenseService;
    @Autowired
    @Qualifier("licenseCategoryService")
    private LicenseCategoryService licenseCategoryService;
    @Autowired
    @Qualifier("licenseSubCategoryService")
    private LicenseSubCategoryService licenseSubCategoryService;
    @Autowired
    @Qualifier("unitOfMeasurementService")
    private UnitOfMeasurementService unitOfMeasurementService;
    @Autowired
    private FeeMatrixService feeMatrixService;

    public EditTradeLicenseAction() {
        this.tradeLicense.setLicensee(new Licensee());
    }

    @Override
    public License getModel() {
        return this.tradeLicense;
    }

    public void setModel(TradeLicense tradeLicense) {
        this.tradeLicense = tradeLicense;
    }

    public void prepareBeforeEdit() {
        this.LOGGER.debug("Entering in the prepareBeforeEdit method:<<<<<<<<<<>>>>>>>>>>>>>:");
        this.prepareNewForm();
        this.setDocumentTypes(this.tradeLicenseService.getDocumentTypesByTransaction(TRANSACTIONTYPE_CREATE_LICENSE));
        Long id = null;
        if (this.tradeLicense.getId() == null)
            if (this.getSession().get("model.id") != null) {
                id = (Long) this.getSession().get("model.id");
                this.getSession().remove("model.id");
            } else
                id = this.tradeLicense.getId();
        else
            id = this.tradeLicense.getId();
        this.tradeLicense = (TradeLicense) this.persistenceService.find("from TradeLicense where id = ?", id);
        if (this.tradeLicense.getOldLicenseNumber() != null)
            this.isOldLicense = StringUtils.isNotBlank(this.tradeLicense.getOldLicenseNumber());
        Boundary licenseboundary = this.boundaryService.getBoundaryById(this.tradeLicense.getBoundary().getId());
        List cityZoneList = new ArrayList();
        //  cityZoneList = licenseUtils.getAllZone();
        this.tradeLicense.setLicenseZoneList(cityZoneList);
        if (licenseboundary.getName().contains("Zone"))
            this.addDropdownData(Constants.DROPDOWN_DIVISION_LIST_LICENSE, Collections.EMPTY_LIST);
        else if (this.tradeLicense.getLicensee().getBoundary() != null)
            this.addDropdownData(Constants.DROPDOWN_DIVISION_LIST_LICENSE,
                    new ArrayList(this.tradeLicense.getBoundary().getParent().getChildren()));


        Long userId = this.securityUtils.getCurrentUser().getId();
        if (userId != null)
            this.setRoleName(this.licenseUtils.getRolesForUserId(userId));

        this.LOGGER.debug("Exiting from the prepareBeforeEdit method:<<<<<<<<<<>>>>>>>>>>>>>:");

        this.setOwnerShipTypeMap(Constants.OWNERSHIP_TYPE);
        List<Boundary> localityList = this.boundaryService.getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(
                LOCALITY, LOCATION_HIERARCHY_TYPE);
        this.addDropdownData("localityList", localityList);
        this.addDropdownData("tradeTypeList", this.tradeLicenseService.getAllNatureOfBusinesses());
        this.addDropdownData("categoryList", this.licenseCategoryService.findAll());
        this.addDropdownData("uomList", this.unitOfMeasurementService.findAllActiveUOM());

        CommonTradeLicenseAjaxAction ajaxTradeLicenseAction = new CommonTradeLicenseAjaxAction();
        this.populateSubCategoryList(ajaxTradeLicenseAction, this.tradeLicense.getCategory() != null);

    }

    /**
     * @param ajaxTradeLicenseAction
     * @param categoryPopulated
     */
    protected void populateSubCategoryList(CommonTradeLicenseAjaxAction ajaxTradeLicenseAction, boolean categoryPopulated) {
        if (categoryPopulated) {
            ajaxTradeLicenseAction.setCategoryId(this.tradeLicense.getCategory().getId());
            ajaxTradeLicenseAction.setLicenseSubCategoryService(this.licenseSubCategoryService);
            ajaxTradeLicenseAction.populateSubCategory();
            this.addDropdownData("subCategoryList", ajaxTradeLicenseAction.getSubCategoryList());
        } else
            this.addDropdownData("subCategoryList", Collections.emptyList());
    }


    @SkipValidation
    @Action(value = "/newtradelicense/editTradeLicense-beforeEdit")
    public String beforeEdit() {
        this.mode = EDIT;
        return NEW;
    }

    public void setupBeforeEdit() {
        this.LOGGER.debug("Entering in the setupBeforeEdit method:<<<<<<<<<<>>>>>>>>>>>>>:");
        this.prepareBeforeEdit();
        this.setupWorkflowDetails();
        this.LOGGER.debug("Exiting from the setupBeforeEdit method:<<<<<<<<<<>>>>>>>>>>>>>:");
    }

    @Override
    public void prepare() {
        if (this.id != null) {
            this.tradeLicense = this.tradeLicenseService.licensePersitenceService().findById(this.id, false);
        }
    }

    @ValidationErrorPageExt(
            action = "edit", makeCall = true, toMethod = "setupBeforeEdit")
    @Action(value = "/newtradelicense/editTradeLicense-edit")
    public String edit() {
        this.LOGGER.debug("Edit Trade License Trade License Elements:<<<<<<<<<<>>>>>>>>>>>>>:" + this.tradeLicense);
        if (this.tradeLicense.getState() == null && !this.isOldLicense)
            this.tradeLicenseService.transitionWorkFlow(this.tradeLicense, this.workflowBean);
        if (!this.isOldLicense)
            this.processWorkflow(NEW);
        if (this.installedMotorList != null) {
            List<MotorDetails> motorDetailsList = new ArrayList<MotorDetails>();
            Iterator<MotorDetails> motorDetails = this.installedMotorList.iterator();
            while (motorDetails.hasNext()) {
                MotorDetails installedMotor = motorDetails.next();
                if (installedMotor != null && installedMotor.getHp() != null && installedMotor.getNoOfMachines() != null
                        && installedMotor.getHp().compareTo(BigDecimal.ZERO) != 0
                        && installedMotor.getNoOfMachines().compareTo(Long.valueOf("0")) != 0) {
                    installedMotor.setLicense(this.tradeLicense);
                    motorDetailsList.add(installedMotor);
                }
            }
            if (!this.tradeLicense.getInstalledMotorList().isEmpty()) {
                for (MotorDetails md : this.tradeLicense.getInstalledMotorList())
                    this.tradeLicense.getInstalledMotorList().remove(this.getPersistenceService().findById(md.getId(), false));
            }
            if (this.installedMotorList != null && !this.installedMotorList.isEmpty()) {
                this.tradeLicense.getInstalledMotorList().clear();
                this.tradeLicense.getInstalledMotorList().addAll(motorDetailsList);
            }
        }

        this.tradeLicenseService.processAndStoreDocument(this.tradeLicense.getDocuments());

        LicenseAppType newAppType = (LicenseAppType) this.persistenceService.find("from  LicenseAppType where name='New' ");
        this.tradeLicense.setLicenseAppType(newAppType);

        this.tradeLicense = (TradeLicense) this.persistenceService.update(this.tradeLicense);
        List<FeeMatrixDetail> feeList = this.feeMatrixService.findFeeList(this.tradeLicense);
        this.totalAmount = this.tradeLicenseService.recalculateDemand(feeList, this.tradeLicense);

		/*
         * if (tradeLicense.getOldLicenseNumber() != null) doAuditing(AuditModule.TL, AuditEntity.TL_LIC, AuditEvent.MODIFIED,
		 * tradeLicense.getAuditDetails());
		 */
        this.LOGGER.debug("Exiting from the edit method:<<<<<<<<<<>>>>>>>>>>>>>:");
        return Constants.MESSAGE;

    }

    @Override
    public boolean acceptableParameterName(String paramName) {
        List<String> nonAcceptable = Arrays.asList("licensee.boundary.parent", "boundary.parent",
                "tradeName.name");
        boolean retValue = super.acceptableParameterName(paramName);
        return retValue ? !nonAcceptable.contains(paramName) : retValue;
    }

    public WorkflowBean getWorkflowBean() {
        return this.workflowBean;
    }

    public void setWorkflowBean(WorkflowBean workflowBean) {
        this.workflowBean = workflowBean;
    }

    @Override
    protected License license() {
        return this.tradeLicense;
    }

    @Override
    protected AbstractLicenseService licenseService() {
        return this.tradeLicenseService;
    }

    public boolean getIsOldLicense() {
        return this.isOldLicense;
    }

    public void setIsOldLicense(boolean isOldLicense) {
        this.isOldLicense = isOldLicense;
    }

    public List<LicenseDocumentType> getDocumentTypes() {
        return this.documentTypes;
    }

    public void setDocumentTypes(List<LicenseDocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Map<String, String> getOwnerShipTypeMap() {
        return this.ownerShipTypeMap;
    }

    public void setOwnerShipTypeMap(Map<String, String> ownerShipTypeMap) {
        this.ownerShipTypeMap = ownerShipTypeMap;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MotorDetails> getInstalledMotorList() {
        return this.installedMotorList;
    }

    public void setInstalledMotorList(List<MotorDetails> installedMotorList) {
        this.installedMotorList = installedMotorList;
    }

}