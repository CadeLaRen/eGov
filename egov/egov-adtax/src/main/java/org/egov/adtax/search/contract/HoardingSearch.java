/* eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.adtax.search.contract;

import java.math.BigDecimal;
import java.util.Date;

import org.egov.adtax.entity.enums.HoardingStatus;

public class HoardingSearch {
    private String hoardingNumber;
    private String applicationNumber;
    private String permissionNumber;
    private String agencyName;
    private Long agency;
    private Date applicationFromDate;
    private Date applicationToDate;
    private Long category;
    private Long subCategory;
    private Long revenueInspector;
    private Long adminBoundryParent;
    private Long adminBoundry;
    private HoardingStatus status;
    private BigDecimal pendingDemandAmount;
    private BigDecimal penaltyAmount;
    private int totalHoardingInAgency;
    private String hordingIdsSearchedByAgency;
    public String getHoardingNumber() {
        return hoardingNumber;
    }

    public void setHoardingNumber(final String hoardingNumber) {
        this.hoardingNumber = hoardingNumber;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(final String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getPermissionNumber() {
        return permissionNumber;
    }

    public void setPermissionNumber(final String permissionNumber) {
        this.permissionNumber = permissionNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getAgency() {
        return agency;
    }

    public void setAgency(final Long agency) {
        this.agency = agency;
    }

    public Date getApplicationFromDate() {
        return applicationFromDate;
    }

    public void setApplicationFromDate(final Date applicationFromDate) {
        this.applicationFromDate = applicationFromDate;
    }

    public Date getApplicationToDate() {
        return applicationToDate;
    }

    public void setApplicationToDate(final Date applicationToDate) {
        this.applicationToDate = applicationToDate;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(final Long category) {
        this.category = category;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(final Long subCategory) {
        this.subCategory = subCategory;
    }

    public Long getRevenueInspector() {
        return revenueInspector;
    }

    public void setRevenueInspector(final Long revenueInspector) {
        this.revenueInspector = revenueInspector;
    }

    public Long getAdminBoundryParent() {
        return adminBoundryParent;
    }

    public void setAdminBoundryParent(final Long adminBoundryParent) {
        this.adminBoundryParent = adminBoundryParent;
    }

    public Long getAdminBoundry() {
        return adminBoundry;
    }

    public void setAdminBoundry(final Long adminBoundry) {
        this.adminBoundry = adminBoundry;
    }

    public HoardingStatus getStatus() {
        return status;
    }

    public void setStatus(final HoardingStatus status) {
        this.status = status;
    }

    public BigDecimal getPendingDemandAmount() {
        return pendingDemandAmount;
    }

    public void setPendingDemandAmount(BigDecimal pendingDemandAmount) {
        this.pendingDemandAmount = pendingDemandAmount;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public int getTotalHoardingInAgency() {
        return totalHoardingInAgency;
    }

    public void setTotalHoardingInAgency(int totalHoardingInAgency) {
        this.totalHoardingInAgency = totalHoardingInAgency;
    }

    public String getHordingIdsSearchedByAgency() {
        return hordingIdsSearchedByAgency;
    }

    public void setHordingIdsSearchedByAgency(String hordingIdsSearchedByAgency) {
        this.hordingIdsSearchedByAgency = hordingIdsSearchedByAgency;
    }
    
}
