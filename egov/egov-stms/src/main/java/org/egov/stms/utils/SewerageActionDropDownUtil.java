/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2016>  eGovernments Foundation

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
package org.egov.stms.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.stms.elasticSearch.entity.SewerageSearchResult;
import org.egov.stms.utils.constants.SewerageTaxConstants;

public class SewerageActionDropDownUtil {

    private static Logger LOGGER = Logger.getLogger(SewerageActionDropDownUtil.class);
    public static final String DEFAULT = "DEFAULT";
    
    public static final Map<String, Map<String, String>> actionUrlMap = new HashMap<String, Map<String, String>>();
    public static final Map<String, List<String>> STATUSACTIONMAP = new HashMap<String, List<String>>();
    public static final Map<String, List<String>> SEWERAGEROLEACTIONMAP = new HashMap<String, List<String>>();

    static {
        // Status wise define different actions.
        STATUSACTIONMAP.put("CREATED", Arrays.asList(SewerageTaxConstants.VIEW));
        STATUSACTIONMAP.put("COLLECTINSPECTIONFEE",Arrays.asList(SewerageTaxConstants.VIEW, SewerageTaxConstants.COLLECTDONATIONCHARHGES));

        //Rolewise define action mappings
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_SEWERAGETAX_CREATOR, Arrays.asList(SewerageTaxConstants.VIEW));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_SEWERAGETAX_ADMINISTRATOR, Arrays.asList(SewerageTaxConstants.VIEW));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_SEWERAGETAX_APPROVER, Arrays.asList(SewerageTaxConstants.VIEW));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_CSCOPERTAOR, Arrays.asList(SewerageTaxConstants.VIEW, SewerageTaxConstants.COLLECTDONATIONCHARHGES));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_ULBOPERATOR, Arrays.asList(SewerageTaxConstants.VIEW, SewerageTaxConstants.COLLECTDONATIONCHARHGES));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_BILLCOLLECTOR, Arrays.asList(SewerageTaxConstants.VIEW, SewerageTaxConstants.COLLECTDONATIONCHARHGES));
        SEWERAGEROLEACTIONMAP.put(SewerageTaxConstants.ROLE_SUPERUSER,Arrays.asList(SewerageTaxConstants.VIEW, SewerageTaxConstants.COLLECTDONATIONCHARHGES));
        SEWERAGEROLEACTIONMAP.put(DEFAULT, Arrays.asList(SewerageTaxConstants.VIEW));

        //For each action, define url mapping
        actionUrlMap.put(SewerageTaxConstants.VIEW,getActionWithUrl(SewerageTaxConstants.VIEWURL, SewerageTaxConstants.VIEW));
        actionUrlMap.put(SewerageTaxConstants.COLLECTDONATIONCHARHGES,getActionWithUrl(SewerageTaxConstants.COLLECTDONATIONCHARHGESURL,
                        SewerageTaxConstants.COLLECTDONATIONCHARHGES));

    }

    private static Map<String, String> getActionWithUrl(String url, String action) {
        Map<String, String> actionwithurl = new LinkedHashMap<String, String>();
        actionwithurl.put(url, action);
        return actionwithurl;
    }

    public static Map<String, String> filterActionsByStatus(List<String> actions, String status) {
        if (actions != null && !actions.isEmpty()) {
            Map<String, String> result = new LinkedHashMap<String, String>();

            List<String> statusActionList = Collections.EMPTY_LIST;

            if (status != null && !status.equals("")) {
                LOGGER.info(" ************ registrationStatus  " + status);
                statusActionList = STATUSACTIONMAP.get(status.toUpperCase());
                LOGGER.info(" ....... statusActionList  " + statusActionList);
                for (String action : actions) {
                    if (statusActionList != null && statusActionList.contains(action)) {
                        result.putAll(actionUrlMap.get(action));
                    }
                }

                return result;
            } else
                return Collections.EMPTY_MAP;
        } else
            return Collections.EMPTY_MAP;
    }

    public static Map<String, String> getActionsByRoles(List<String> roleName, String collectionStatus) {
        List<String> actionList = Collections.EMPTY_LIST;
        
        LOGGER.debug(" ************ Role Name " + roleName);
        LOGGER.debug(" ************ registrationStatus  " + collectionStatus);
      
        if (roleName != null && !roleName.isEmpty()) {
            if (roleName.contains(SewerageTaxConstants.ROLE_SEWERAGETAX_CREATOR)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_SEWERAGETAX_CREATOR);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_SUPERUSER)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_SUPERUSER);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_SEWERAGETAX_ADMINISTRATOR)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_SEWERAGETAX_ADMINISTRATOR);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_SEWERAGETAX_APPROVER)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_SEWERAGETAX_APPROVER);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_CSCOPERTAOR)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_CSCOPERTAOR);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_ULBOPERATOR)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_ULBOPERATOR);
            } else if (roleName.contains(SewerageTaxConstants.ROLE_BILLCOLLECTOR)) {
                actionList = SEWERAGEROLEACTIONMAP.get(SewerageTaxConstants.ROLE_BILLCOLLECTOR);
            } else {
                actionList = SEWERAGEROLEACTIONMAP.get(DEFAULT);
            }
        }
        return filterActionsByStatus(actionList, collectionStatus);
    }

    public static final SewerageSearchResult getSearchResultWithActions(List<String> roleName, final String status) {
        SewerageSearchResult searchActions = new SewerageSearchResult();
        if (status != null)
            searchActions.setActions(getActionsByRoles(roleName, status));
        return searchActions;
    }

}