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
package org.egov.wtms.application.service.collection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.egov.collection.constants.CollectionConstants;
import org.egov.demand.dao.EgBillDao;
import org.egov.demand.dao.EgDemandDao;
import org.egov.demand.interfaces.Billable;
import org.egov.demand.model.AbstractBillable;
import org.egov.demand.model.EgBillType;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.domain.model.BoundaryDetails;
import org.egov.ptis.domain.model.OwnerName;
import org.egov.ptis.domain.service.property.PropertyExternalService;
import org.egov.wtms.application.entity.WaterConnectionDetails;
import org.egov.wtms.application.service.ConnectionDemandService;
import org.egov.wtms.masters.entity.enums.ConnectionStatus;
import org.egov.wtms.utils.PropertyExtnUtils;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WaterConnectionBillable extends AbstractBillable implements Billable {

    private static final String STRING_DEPARTMENT_CODE = "REV";
    private static final String STRING_SERVICE_CODE = "WT";
    public static final String DEFAULT_FUNCTIONARY_CODE = "1";
    public static final String DEFAULT_FUND_SRC_CODE = "01";
    public static final String DEFAULT_FUND_CODE = "01";
    private static final String DISPLAY_MESSAGE = "Water Charge Collection";
    private WaterConnectionDetails WaterConnectionDetails;
    private AssessmentDetails assessmentDetails;
    private Long userId;
    private EgBillType billType;
    private Boolean isCallbackForApportion = Boolean.FALSE;
    private String referenceNumber;
    private String transanctionReferenceNumber;

    @Autowired
    private PropertyExtnUtils propertyExtnUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private EgBillDao egBillDAO;
    @Autowired
    private EgDemandDao egDemandDAO;

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ConnectionDemandService connectioDemanService;

    @Override
    public String getBillPayee() {
        return buildOwnerFullName(getAssessmentDetails().getOwnerNames());
    }

    @Override
    public String getBillAddress() {
        final AssessmentDetails assessmentDetails = propertyExtnUtils.getAssessmentDetailsForFlag(
                getWaterConnectionDetails().getConnection().getPropertyIdentifier(), PropertyExternalService.FLAG_FULL_DETAILS);
        return buildAddressDetails(assessmentDetails);
    }

    @Override
    public EgDemand getCurrentDemand() {
        return getWaterConnectionDetails().getDemand();
    }

    @Override
    public List<EgDemand> getAllDemands() {
        List<EgDemand> demands = null;
       final Long demandIds = getCurrentDemand().getId();
        if (demandIds != null ) {
            demands = new ArrayList<EgDemand>();
            
                demands.add(egDemandDAO.findById(Long.valueOf(demandIds.toString()), false));
        }
        return demands;
    }

    @Override
    public EgBillType getBillType() {
        if (billType == null)
            if (getUserId() != null && !getUserId().equals("")) {
                final String loginUser = userService.getUserById(getUserId()).getName();
                if (!loginUser.equals(WaterTaxConstants.CITIZENUSER))
                    billType = egBillDAO.getBillTypeByCode(WaterTaxConstants.BILLTYPE_MANUAL);
            }
        return billType;
    }

    @Override
    public Date getBillLastDueDate() {
        return new DateTime().plusMonths(1).toDate();
    }

    @Override
    public Long getBoundaryNum() {
        return getAssessmentDetails().getBoundaryDetails().getWardNumber();
    }

    @Override
    public String getBoundaryType() {
        return "Ward";
    }

    @Override
    public String getDepartmentCode() {
        return STRING_DEPARTMENT_CODE;
    }

    @Override
    public BigDecimal getFunctionaryCode() {
        return new BigDecimal(DEFAULT_FUNCTIONARY_CODE);
    }

    @Override
    public String getFundCode() {
        return DEFAULT_FUND_CODE;
    }

    @Override
    public String getFundSourceCode() {
        return DEFAULT_FUND_SRC_CODE;
    }

    @Override
    public Date getIssueDate() {
        return new Date();
    }

    @Override
    public Date getLastDate() {
        return getBillLastDueDate();
    }

    @Override
    public Module getModule() {
        return moduleService.getModuleByName(WaterTaxConstants.EGMODULE_NAME);
    }

    @Override
    public Boolean getOverrideAccountHeadsAllowed() {
        return false;
    }

    @Override
    public Boolean getPartPaymentAllowed() {
        return true;
    }

    @Override
    public String getServiceCode() {
        return STRING_SERVICE_CODE;
    }

    @Override
    public BigDecimal getTotalAmount() {
        final EgDemand currentDemand = getCurrentDemand();
        final List instVsAmt = connectioDemanService.getDmdCollAmtInstallmentWise(currentDemand);
        BigDecimal balance = BigDecimal.ZERO;
        for (final Object object : instVsAmt) {
            final Object[] ddObject = (Object[]) object;
            final BigDecimal dmdAmt = (BigDecimal) ddObject[2];
            BigDecimal collAmt = BigDecimal.ZERO;
            if (ddObject[2] != null)
                collAmt = new BigDecimal((Double) ddObject[3]);
            balance = balance.add(dmdAmt.subtract(collAmt));

        }
        return balance;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getDescription() {
        if (null != getWaterConnectionDetails().getConnection().getConsumerCode())
            return "Water Charge H.S.C No: " + getWaterConnectionDetails().getConnection().getConsumerCode();
        else
            return "Water Charge Application Number: " + getWaterConnectionDetails().getApplicationNumber();
    }

    @Override
    public String getDisplayMessage() {
        return DISPLAY_MESSAGE;
    }

    @Override
    public String getCollModesNotAllowed() {
            
            StringBuilder collectionModesNotAllowed = new StringBuilder();
            collectionModesNotAllowed.append(CollectionConstants.INSTRUMENTTYPE_BANK);
            return collectionModesNotAllowed.toString();
    }

    @Override
    public String getConsumerId() {
        return getWaterConnectionDetails().getApplicationNumber();
    }

    @Override
    public Boolean isCallbackForApportion() {
        return isCallbackForApportion;
    }

    @Override
    public void setCallbackForApportion(final Boolean b) {
        isCallbackForApportion = b;
    }

    public WaterConnectionDetails getWaterConnectionDetails() {
        return WaterConnectionDetails;
    }

    public void setWaterConnectionDetails(final WaterConnectionDetails waterConnectionDetails) {
        WaterConnectionDetails = waterConnectionDetails;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setBillType(final EgBillType billType) {
        this.billType = billType;
    }

    public String buildOwnerFullName(final Set<OwnerName> ownerSet) {
        if (ownerSet == null)
            throw new ApplicationRuntimeException("Property Owner set is null...");
        String ownerFullName = "";
        final Set<String> ownerNameSet = new HashSet<String>();
        for (final OwnerName propOwnerInfo : ownerSet)
            // User propOwner = propOwnerInfo.getOwner();
            if (propOwnerInfo.getOwnerName() != null && !propOwnerInfo.getOwnerName().trim().equals(""))
                if (!ownerNameSet.contains(propOwnerInfo.getOwnerName().trim())) {
                    if (!ownerFullName.trim().equals(""))
                        if (!ownerFullName.equals(""))
                            ownerFullName += ", ";
                    ownerNameSet.add(propOwnerInfo.getOwnerName().trim());
                    ownerFullName = propOwnerInfo.getOwnerName() == null ? "" : propOwnerInfo.getOwnerName();
                }
        return ownerFullName;
    }

    public String buildAddressDetails(final AssessmentDetails assessmentDetails) {
        final BoundaryDetails boundaryDetails = assessmentDetails.getBoundaryDetails();
        final StringBuilder address = new StringBuilder();
        if (boundaryDetails.getZoneName() != null)
            address.append(boundaryDetails.getZoneName());
        if (boundaryDetails.getWardName() != null)
            address.append(", ").append(boundaryDetails.getWardName());
        if (boundaryDetails.getLocalityName() != null)
            address.append(", ").append(boundaryDetails.getLocalityName());
        if (boundaryDetails.getBlockName() != null)
            address.append(", ").append(boundaryDetails.getBlockName());
        if (boundaryDetails.getStreetName() != null)
            address.append(", ").append(boundaryDetails.getStreetName());
        return address.toString();
    }

    public AssessmentDetails getAssessmentDetails() {
        return assessmentDetails;
    }

    public void setAssessmentDetails(final AssessmentDetails assessmentDetails) {
        this.assessmentDetails = assessmentDetails;
    }

    @Override
    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    @Override
    public String getTransanctionReferenceNumber() {
        return transanctionReferenceNumber;
    }

    public void setTransanctionReferenceNumber(String transanctionReferenceNumber) {
        this.transanctionReferenceNumber = transanctionReferenceNumber;
    }
}
