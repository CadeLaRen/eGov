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
package org.egov.stms.elasticSearch.entity;

import static org.egov.search.domain.Filter.queryStringFilter;
import static org.egov.search.domain.Filter.termsStringFilter;

import java.util.ArrayList;
import java.util.List;

import org.egov.search.domain.Filter;
import org.egov.search.domain.Filters;
import org.jboss.logging.Logger;

public class SewerageConnSearchRequest {
	private String searchText;
    private String dhscNumber;
    private String moduleName;
    private String applicationType;
    private String applicantName;
    private String mobileNumber;
    private String revenueWard;
    private String doorNumber;
    private String ulbName;
    private String applicationDate;
    
    private static final Logger logger = Logger.getLogger(SewerageConnSearchRequest.class);
    
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(final String applicationType) {
        this.applicationType = applicationType;
    }
    
    public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getUlbName() {
		return ulbName;
	}
	
	public String getDhscNumber() {
		return dhscNumber;
	}

	public void setDhscNumber(String shscNumber) {
		this.dhscNumber = shscNumber;
	}

	public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public Filters searchFilters() { //TODO: MOVE THESE CONSTANTS TO COMMON FILE.
        final List<Filter> andFilters = new ArrayList<>(0);
        andFilters.add(termsStringFilter("searchable.dhscnumber", dhscNumber));
        andFilters.add(termsStringFilter("clauses.cityname", ulbName));
        andFilters.add(queryStringFilter("searchable.consumername", applicantName));
        andFilters.add(queryStringFilter("clauses.mobilenumber", mobileNumber));
        andFilters.add(termsStringFilter("clauses.doorno", doorNumber));
        andFilters.add(termsStringFilter("clauses.revwardname", revenueWard));
        andFilters.add(queryStringFilter("clauses.applicationdate",applicationDate));
        if (logger.isDebugEnabled())
            logger.debug("finished filters");
        return Filters.withAndFilters(andFilters);
    }
    
    public String searchQuery() {
        return searchText;
    }

    public String getRevenueWard() {
        return revenueWard;
    }

    public void setRevenueWard(String revenueWard) {
        this.revenueWard = revenueWard;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public void setUlbName(String ulbName) {
		this.ulbName = ulbName;
	}


}
