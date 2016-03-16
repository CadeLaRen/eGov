/**
 * eGov suite of products aim to improve the internal efficiency,transparency, 
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation 
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or 
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

	1) All versions of this program, verbatim or modified must carry this 
	   Legal Notice.

	2) Any misrepresentation of the origin of the material is prohibited. It 
	   is required that all modified versions of this material be marked in 
	   reasonable ways as different from the original version.

	3) This license does not grant any rights to any user of the program 
	   with regards to rights under trademark law for use of the trade names 
	   or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.models.extd;

// Generated 13 Nov, 2012 12:35:05 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.egov.bpa.constants.BpaConstants;
import org.egov.bpa.models.extd.masters.DocumentHistoryExtn;
import org.egov.bpa.models.extd.masters.ServiceTypeExtn;
import org.egov.commons.EgwStatus;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.persistence.validator.annotation.Required;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.portal.entity.Citizen;


/*
import org.egov.portal.surveyor.model.Surveyor;*/

/**
 * Registration generated by hbm2java
 */
public class RegistrationExtn extends StateAware {

	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	//private Surveyor surveyorName;
	private String baNum;
	private Date baOrderDate;
	@Required(message = "planSubmissionNum.required")
	private String planSubmissionNum;
	@Required(message = "planSubmissionDate.required")
	private Date planSubmissionDate;
	private String cmdaNum;
	private Date cmdaRefDate;
	@Required(message = "appType.required")
	private String appType;
	@Required(message = "appMode.required")
	private String appMode;
	private String propertyid;
	private RegistrationExtn parent;
	@Required(message = "owner.required")
	private Citizen owner;
	@Required(message = "serviceType.required")
	private ServiceTypeExtn serviceType;
	private EgDemand egDemand;
	@Required(message = "egwStatus.required")
	private EgwStatus egwStatus;
	private RegnDetailsExtn regnDetails;
	private Boundary adminboundaryid;
	@Required(message = "locboundaryid.required")
	private Boundary locboundaryid;
	private BigDecimal admissionfeeAmount;
	private Set<BpaAddressExtn> bpaAddressSet = new HashSet<BpaAddressExtn>();
	private Set<LetterToPartyExtn> letterToPartySet = new HashSet<LetterToPartyExtn>();
	private Set<RegnStatusDetailsExtn> regnStatusDetailsSet = new HashSet<RegnStatusDetailsExtn>();
	private Set<DocumentHistoryExtn> documenthistorySet = new HashSet<DocumentHistoryExtn>(0);
	private Set<RegnAutoDcrDtlsExtn> autoDcrSet = new HashSet<RegnAutoDcrDtlsExtn>();
	private Set<ApprdBuildingDetailsExtn> apprdBuildingDetailsSet = new HashSet<ApprdBuildingDetailsExtn>();
	private Set<InspectionExtn> inspectionSet = new HashSet<InspectionExtn>();
	private Set<LpChecklistExtn> lpchecklistSet = new HashSet<LpChecklistExtn>();
	private Set<RegistrationChecklistExtn> registrationChecklistSet = new HashSet<RegistrationChecklistExtn>();
	private LetterToPartyExtn letterToParty;
	private transient String additionalRule;
	private String documentid;
	private Long approverId;
	private String additionalState;
	private RejectionExtn rejection;
	private String statusChangeRemarks;
	private EgwStatus oldStatus;
	private String planPermitApprovalNum;
	private String previousObjectState;
	private String previousObjectAction;
	private Integer previousStateOwnerId;
	private Boolean isSanctionFeeRaised;
	private String securityKey;
	private List<String> registerBpaSearchActions = new ArrayList<String>();
	private String existingBANum;
	private String existingPPANum;
	private String request_number;
	private Long serviceRegistryId;
private Long ApproverPositionId;
	private RegnStatusDetailsExtn signDetails;
	private RegnStatusDetailsExtn orderDetails;
	private RegnStatusDetailsExtn orderIssueDet;
	private RegnStatusDetailsExtn rejectOrdPrepDet;
	private RegnStatusDetailsExtn rejectOrdIssDet;
	private RegnStatusDetailsExtn challanDetails;
	private String feeRemarks;
	private Set<RegistrationDDDetailsExtn> feeDDSet = new HashSet<RegistrationDDDetailsExtn>();
	private Set<RegnApprovalInformationExtn> approvalInfoSet = new HashSet<RegnApprovalInformationExtn>();
	private Date externalfeecollectedDate;
	private String feeDate;
	private String registrationFeeChallanNumber;
	private String initialPlanSubmissionNum;
	
	
	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public Long getApproverPositionId() {
		return ApproverPositionId;
	}

	public void setApproverPositionId(Long approverPositionId) {
		ApproverPositionId = approverPositionId;
	}

	public String getInitialPlanSubmissionNum() {
		return initialPlanSubmissionNum;
	}

	public void setInitialPlanSubmissionNum(String initialPlanSubmissionNum) {
		this.initialPlanSubmissionNum = initialPlanSubmissionNum;
	}

	public Long getServiceRegistryId() {
		return serviceRegistryId;
	}

	public void setServiceRegistryId(Long serviceRegistryId) {
		this.serviceRegistryId = serviceRegistryId;
	}

	public String getRequest_number() {
		return request_number;
	}

	public void setRequest_number(String request_number) {
		this.request_number = request_number;
	}

	public Boolean getIsSanctionFeeRaised() {
		return isSanctionFeeRaised;
	}

	public void setIsSanctionFeeRaised(Boolean isSanctionFeeRaised) {
		this.isSanctionFeeRaised = isSanctionFeeRaised;
	}

	public String getRegistrationFeeChallanNumber() {
		return registrationFeeChallanNumber;
	}

	public void setRegistrationFeeChallanNumber(String registrationFeeChallanNumber) {
		this.registrationFeeChallanNumber = registrationFeeChallanNumber;
	}

	public String getFeeDate() {
		return feeDate;
	}

	public void setFeeDate(String feeDate) {
		this.feeDate = feeDate;
	}

	public Date getExternalfeecollectedDate() {
		return externalfeecollectedDate;
	}

	public void setExternalfeecollectedDate(Date externalfeecollectedDate) {
		this.externalfeecollectedDate = externalfeecollectedDate;
	}

	public Set<RegistrationDDDetailsExtn> getFeeDDSet() {
		return feeDDSet;
	}

	public void setFeeDDSet(Set<RegistrationDDDetailsExtn> feeDDSet) {
		this.feeDDSet = feeDDSet;
	}

	public String getFeeRemarks() {
		return feeRemarks;
	}

	public void setFeeRemarks(String feeRemarks) {
		this.feeRemarks = feeRemarks;
	}



	public String getAdditionalState() {
		return additionalState;
	}

	public void setAdditionalState(String additionalState) {
		this.additionalState = additionalState;
	}

	public EgwStatus getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(EgwStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getStatusChangeRemarks() {
		return statusChangeRemarks;
	}

	public void setStatusChangeRemarks(String statusChangeRemarks) {
		this.statusChangeRemarks = statusChangeRemarks;
	}

	public RejectionExtn getRejection() {
		return rejection;
	}

	public void setRejection(RejectionExtn rejection) {
		this.rejection = rejection;
	}

	
	public String getBaNum() {
		return baNum;
	}

	public void setBaNum(String baNum) {
		this.baNum = baNum;
	}

	public Date getBaOrderDate() {
		return baOrderDate;
	}

	public void setBaOrderDate(Date baOrderDate) {
		this.baOrderDate = baOrderDate;
	}

	public String getCmdaNum() {
		return cmdaNum;
	}

	public void setCmdaNum(String cmdaNum) {
		this.cmdaNum = cmdaNum;
	}

	public Date getCmdaRefDate() {
		return cmdaRefDate;
	}

	public void setCmdaRefDate(Date cmdaRefDate) {
		this.cmdaRefDate = cmdaRefDate;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPropertyid() {
		return propertyid;
	}

	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}

	public RegistrationExtn getParent() {
		return parent;
	}

	public void setParent(RegistrationExtn parent) {
		this.parent = parent;
	}

	public ServiceTypeExtn getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeExtn serviceType) {
		this.serviceType = serviceType;
	}

	public EgDemand getEgDemand() {
		return egDemand;
	}

	public void setEgDemand(EgDemand egDemand) {
		this.egDemand = egDemand;
	}

	public EgwStatus getEgwStatus() {
		return egwStatus;
	}

	public void setEgwStatus(EgwStatus egwStatus) {
		this.egwStatus = egwStatus;
	}

	public Set<LetterToPartyExtn> getLetterToPartySet() {
		return letterToPartySet;
	}

	public void setLetterToPartySet(Set<LetterToPartyExtn> letterToPartySet) {
		this.letterToPartySet = letterToPartySet;
	}

	public void addLetterToParty(LetterToPartyExtn lp) {
		getLetterToPartySet().add(lp);
	}

	public void removeLetterToParty(LetterToPartyExtn lp) {
		getLetterToPartySet().remove(lp);
	}

	public Set<RegnStatusDetailsExtn> getRegnStatusDetailsSet() {
		return regnStatusDetailsSet;
	}

	public void setRegnStatusDetailsSet(Set<RegnStatusDetailsExtn> regnStatusDetailsSet) {
		this.regnStatusDetailsSet = regnStatusDetailsSet;
	}

	public void addRegnStatusDetails(RegnStatusDetailsExtn regnStatusDetails) {
		getRegnStatusDetailsSet().add(regnStatusDetails);
	}

	public void removeRegnStatusDetails(RegnStatusDetailsExtn regnStatusDetails) {
		getRegnStatusDetailsSet().remove(regnStatusDetails);
	}

	public Set<RegnAutoDcrDtlsExtn> getAutoDcrSet() {
		return autoDcrSet;
	}

	public void setAutoDcrSet(Set<RegnAutoDcrDtlsExtn> autoDcrSet) {
		this.autoDcrSet = autoDcrSet;
	}

	public void addAutoDcr(RegnAutoDcrDtlsExtn autoDcr) {
		getAutoDcrSet().add(autoDcr);
	}

	public void removeAutoDcr(RegnAutoDcrDtlsExtn autoDcr) {
		getAutoDcrSet().remove(autoDcr);
	}

	public Set<ApprdBuildingDetailsExtn> getApprdBuildingDetailsSet() {
		return apprdBuildingDetailsSet;
	}

	public void setApprdBuildingDetailsSet(Set<ApprdBuildingDetailsExtn> apprdBuildingDetailsSet) {
		this.apprdBuildingDetailsSet = apprdBuildingDetailsSet;
	}

	public void addApprdBuildingDetails(ApprdBuildingDetailsExtn apprdBuildingDetails) {
		getApprdBuildingDetailsSet().add(apprdBuildingDetails);
	}

	public void removeApprdBuildingDetails(ApprdBuildingDetailsExtn apprdBuildingDetails) {
		getApprdBuildingDetailsSet().remove(apprdBuildingDetails);
	}

	public Set<InspectionExtn> getInspectionSet() {
		return inspectionSet;
	}

	public void setInspectionSet(Set<InspectionExtn> inspectionSet) {
		this.inspectionSet = inspectionSet;
	}

	public void addInspection(InspectionExtn inspection) {
		getInspectionSet().add(inspection);
	}

	public void removeInspection(InspectionExtn inspection) {
		getInspectionSet().remove(inspection);
	}

	public Set<LpChecklistExtn> getLpchecklistSet() {
		return lpchecklistSet;
	}

	public void setLpchecklistSet(Set<LpChecklistExtn> lpchecklistSet) {
		this.lpchecklistSet = lpchecklistSet;
	}

	public void addLpChecklist(LpChecklistExtn lpChecklist) {
		getLpchecklistSet().add(lpChecklist);
	}

	public void removeLpChecklist(LpChecklistExtn lpChecklist) {
		getLpchecklistSet().remove(lpChecklist);
	}

	public Set<RegistrationChecklistExtn> getRegistrationChecklistSet() {
		return registrationChecklistSet;
	}

	public void setRegistrationChecklistSet(Set<RegistrationChecklistExtn> registrationChecklistSet) {
		this.registrationChecklistSet = registrationChecklistSet;
	}

	public void addRegistrationChecklist(RegistrationChecklistExtn registrationChecklist) {
		getRegistrationChecklistSet().add(registrationChecklist);
	}

	public void removeRegistrationChecklist(RegistrationChecklistExtn registrationChecklist) {
		getRegistrationChecklistSet().remove(registrationChecklist);
	}

	@Override
	public String getStateDetails() {
		StringBuffer registrationStateDetail= new StringBuffer();
		registrationStateDetail.append(getPlanSubmissionNum());

		if (getEgwStatus() != null )
		{
			if (getInitialPlanSubmissionNum()!=null)
				registrationStateDetail.append("-").append(getInitialPlanSubmissionNum());
				 			
			if(getEgwStatus().getDescription() != null)
				registrationStateDetail.append("-").append(getEgwStatus().getDescription());
		}
	
	 return registrationStateDetail.toString();
	}

	public RegnDetailsExtn getRegnDetails() {
		return regnDetails;
	}

	public void setRegnDetails(RegnDetailsExtn regnDetails) {
		this.regnDetails = regnDetails;
	}

	public Set<BpaAddressExtn> getBpaAddressSet() {
		return bpaAddressSet;
	}

	public void setBpaAddressSet(Set<BpaAddressExtn> bpaAddressSet) {
		this.bpaAddressSet = bpaAddressSet;
	}

	public void addBpaAddress(BpaAddressExtn bpaAddress) {
		getBpaAddressSet().add(bpaAddress);
	}

	public void removeBpaAddress(BpaAddressExtn bpaAddress) {
		getBpaAddressSet().remove(bpaAddress);
	}

	public String getPlanSubmissionNum() {
		return planSubmissionNum;
	}

	public void setPlanSubmissionNum(String planSubmissionNum) {
		this.planSubmissionNum = planSubmissionNum;
	}

	public Date getPlanSubmissionDate() {
		return planSubmissionDate;
	}

	public void setPlanSubmissionDate(Date planSubmissionDate) {
		this.planSubmissionDate = planSubmissionDate;
	}

	public List<String> getDropdownActionList() {
		return BpaConstants.SEARCHDROPDOWNLIST;
	}

	public String getBpaOwnerAddress() {

		return getBpaAddress(BpaConstants.OWNER_ADDRESS);
	}

	public String getBpaSiteAddress() {
		return getBpaAddress(BpaConstants.PROPERTY_ADDRESS);

	}

	public String getApplicantAddress1() {
		for (BpaAddressExtn bpaAddressObj : this.bpaAddressSet) {
			if (null != bpaAddressObj.getAddressTypeMaster() && BpaConstants.OWNER_ADDRESS.equals(bpaAddressObj.getAddressTypeMaster())) {
				return bpaAddressObj.getStreetAddress1();
			}
		}
		return null;
	}

	public String getChangeOfUsageFrom() {
		StringBuffer usageFrom = new StringBuffer();

		for (RegnApprovalInformationExtn usageObj : this.approvalInfoSet) {
			if (null != usageObj && null != usageObj.getUsageFrom()) {
				usageFrom.append(usageObj.getUsageFrom().getName() != null ? usageObj.getUsageFrom().getName() : " ");
			}
		}

		return usageFrom.toString();
	}

	public String getChangeOfUsageTo() {
		StringBuffer usageTo = new StringBuffer();
		for (RegnApprovalInformationExtn usageObj : this.approvalInfoSet) {

			if (null != usageObj && null != usageObj.getUsageTo().getName()) {
				usageTo.append(usageObj.getUsageTo().getName() != null ? usageObj.getUsageTo().getName() : " ");
			}

		}

		return usageTo.toString();
	}

	/*
	 * To get Pincode for Records for Housing Start Up Report..
	 */
	private String getPincode(String addressType) {
		StringBuffer pincode = new StringBuffer();
		for (BpaAddressExtn bpaAddressObj : this.bpaAddressSet) {
			if (null != bpaAddressObj.getAddressTypeMaster() && addressType.equals(bpaAddressObj.getAddressTypeMaster())) {
				pincode.append(bpaAddressObj.getPincode() != null ? bpaAddressObj.getPincode() : "");
			}
		}

		return pincode.toString();
	}

	public String getBpaSitePincode() {
		return getPincode(BpaConstants.PROPERTY_ADDRESS);

	}

	public Citizen getOwner() {
		return owner;
	}

	public void setOwner(Citizen owner) {
		this.owner = owner;
	}

	public Boundary getAdminboundaryid() {
		return adminboundaryid;
	}

	public void setAdminboundaryid(Boundary adminboundaryid) {
		this.adminboundaryid = adminboundaryid;
	}

	public Boundary getLocboundaryid() {
		return locboundaryid;
	}

	public void setLocboundaryid(Boundary locboundaryid) {
		this.locboundaryid = locboundaryid;
	}

	private String getBpaAddress(String addressType) {
		StringBuffer bpaAddress = new StringBuffer();
		for (BpaAddressExtn bpaAddressObj : this.bpaAddressSet) {
			if (null != bpaAddressObj.getAddressTypeMaster() && addressType.equals(bpaAddressObj.getAddressTypeMaster() )) {
				bpaAddress.append(bpaAddressObj.getPlotDoorNumber() != null ? "Door No:" + bpaAddressObj.getPlotDoorNumber() + "," : "");
				bpaAddress.append(bpaAddressObj.getPlotBlockNumber() != null ? "Block No:" + bpaAddressObj.getPlotBlockNumber() + "," : "");
				bpaAddress.append(bpaAddressObj.getPlotNumber() != null ? "Plot No:" + bpaAddressObj.getPlotNumber() + ", " : "");
				bpaAddress.append(bpaAddressObj.getStreetAddress1() != null ? bpaAddressObj.getStreetAddress1() : "");
				if (addressType != null && addressType.equals(BpaConstants.PROPERTY_ADDRESS)) {
					if (this.getLocboundaryid() != null && this.getLocboundaryid().getName() != null) {

						bpaAddress.append(this.getLocboundaryid().getName() + ",");
						bpaAddress.append(this.getLocboundaryid().getParent().getName() + ",");

					}
				}
				bpaAddress.append(bpaAddressObj.getCityTown() != null ? bpaAddressObj.getCityTown() + ", " : "");
				bpaAddress.append(bpaAddressObj.getPincode() != null ? bpaAddressObj.getPincode() : "");

			}
		}
		return bpaAddress.toString();

	}


	public BigDecimal getAdmissionfeeAmount() {
		return admissionfeeAmount;
	}

	public void setAdmissionfeeAmount(BigDecimal admissionfeeAmount) {
		this.admissionfeeAmount = admissionfeeAmount;
	}

	public LetterToPartyExtn getLetterToParty() {
		return letterToParty;
	}

	public void setLetterToParty(LetterToPartyExtn letterToParty) {
		this.letterToParty = letterToParty;
	}

	public String getAdditionalRule() {
		return additionalRule;
	}

	public void setAdditionalRule(String additionalRule) {
		this.additionalRule = additionalRule;
	}

	public String getDocumentid() {
		return documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public String getPlanPermitApprovalNum() {
		return planPermitApprovalNum;
	}

	public void setPlanPermitApprovalNum(String planPermitApprovalNum) {
		this.planPermitApprovalNum = planPermitApprovalNum;
	}

	public String getPreviousObjectState() {
		return previousObjectState;
	}

	public void setPreviousObjectState(String previousObjectState) {
		this.previousObjectState = previousObjectState;
	}

	public Integer getPreviousStateOwnerId() {
		return previousStateOwnerId;
	}

	public void setPreviousStateOwnerId(Integer previousStateOwnerId) {
		this.previousStateOwnerId = previousStateOwnerId;
	}

	public String getPreviousObjectAction() {
		return previousObjectAction;
	}

	public void setPreviousObjectAction(String previousObjectAction) {
		this.previousObjectAction = previousObjectAction;
	}

	public RegnStatusDetailsExtn getSignDetails() {
		return signDetails;
	}

	public void setSignDetails(RegnStatusDetailsExtn signDetails) {
		this.signDetails = signDetails;
	}

	public RegnStatusDetailsExtn getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(RegnStatusDetailsExtn orderDetails) {
		this.orderDetails = orderDetails;
	}

	public RegnStatusDetailsExtn getOrderIssueDet() {
		return orderIssueDet;
	}

	public void setOrderIssueDet(RegnStatusDetailsExtn orderIssueDet) {
		this.orderIssueDet = orderIssueDet;
	}

	public RegnStatusDetailsExtn getRejectOrdPrepDet() {
		return rejectOrdPrepDet;
	}

	public void setRejectOrdPrepDet(RegnStatusDetailsExtn rejectOrdPrepDet) {
		this.rejectOrdPrepDet = rejectOrdPrepDet;
	}

	public RegnStatusDetailsExtn getRejectOrdIssDet() {
		return rejectOrdIssDet;
	}

	public void setRejectOrdIssDet(RegnStatusDetailsExtn rejectOrdIssDet) {
		this.rejectOrdIssDet = rejectOrdIssDet;
	}

	public List<String> getRegisterBpaSearchActions() {
		return registerBpaSearchActions;
	}

	public void setRegisterBpaSearchActions(List<String> registerBpaSearchActions) {
		this.registerBpaSearchActions = registerBpaSearchActions;
	}

	public RegnStatusDetailsExtn getChallanDetails() {
		return challanDetails;
	}

	public void setChallanDetails(RegnStatusDetailsExtn challanDetails) {
		this.challanDetails = challanDetails;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public String getExistingBANum() {
		return existingBANum;
	}

	public void setExistingBANum(String existingBANum) {
		this.existingBANum = existingBANum;
	}

	public String getExistingPPANum() {
		return existingPPANum;
	}

	public void setExistingPPANum(String existingPPANum) {
		this.existingPPANum = existingPPANum;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getAppMode() {
		return appMode;
	}

	public void setAppMode(String appMode) {
		this.appMode = appMode;
	}

	public Set<RegnApprovalInformationExtn> getApprovalInfoSet() {
		return approvalInfoSet;
	}

	public void setApprovalInfoSet(Set<RegnApprovalInformationExtn> approvalInfoSet) {
		this.approvalInfoSet = approvalInfoSet;
	}

	public Set<DocumentHistoryExtn> getDocumenthistorySet() {
		return documenthistorySet;
	}

	public void setDocumenthistorySet(Set<DocumentHistoryExtn> documenthistorySet) {
		this.documenthistorySet = documenthistorySet;
	}
	public void addDocumenthistorySet(DocumentHistoryExtn documenthistory) {
		this.getDocumenthistorySet().add(documenthistory);
	}
	public void removeDocumenthistorySet(DocumentHistoryExtn documenthistory) {
		this.getDocumenthistorySet().remove(documenthistory);
	}

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
	
}