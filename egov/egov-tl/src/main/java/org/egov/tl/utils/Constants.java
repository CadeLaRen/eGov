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

package org.egov.tl.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {
        public static final String FILESTORE_MODULECODE = "TL";
        public static final String NEW = "new";
        public static final String EDIT = "edit";
        public static final String VIEW = "view";
        public static final String ROLE_BILLCOLLECTOR = "Collection Operator";
        public static final String WF_STATE_COLLECTION_PENDING = "Create License:Commissioner Approved";
        public static final String WF_STATE_INSPECTION_APPROVED_STR="Sanitary inspector Approved";
        public static final String WF_STATE_COMMISSIONER_APPROVED_STR="Commissioner Approved";
        public static final String WF_STATE_GENERATE_CERTIFICATE="Create License:generate Certificate";
        public static final String WF_STATE_RENEWAL_COMM_APPROVED="Renewal License:Commissioner Approved";
        public static final String WF_STATE_SANITORY_INSPECTOR_APPROVAL_PENDING = "Sanitary inspector Approve pending";
        public static final String WF_STATE_DIGITAL_SIGN_NEWTL="Create License:Collection Done";
        public static final String WF_STATE_DIGITAL_SIGN_RENEWAL="Renewal License:Collection Done";
        public static final String WF_STATE_DIGISIGN_STR="Collection Done";
        public static final String BUILDINGTYPE_RENTAL_AGREEMANT = "Rental";
        public static final String BUILDINGTYPE_OWN_BUILDING = "Own Building";
        public static final String NEW_LICENSE_REGISTERED = "NEW";
        public static final int PAGE_SIZE = 20;
        public static final String PARAM_PAGE = "page";
        public static final String ZONE_WISE_REPORT = "zoneWiseReport";
        public static final String WARD_WISE_REPORT = "wardWiseReport";
        public static final String LATE_RENEWALS_REPORT = "lateRenewals";
        public static final String TRADE_WISE_REPORT = "tradeWiseReport";
        public static final String ZONE = "Zone";
        public static final String DIVISION = "Ward";
        public static final String FROM = "from";
        public static final String DROPDOWN_DIVISION_LIST_LICENSE = "divisionListLicense";
        public static final String DROPDOWN_DIVISION_LIST_LICENSEE = "divisionListLicensee";
        public static final String DROPDOWN_AREA_LIST_LICENSE = "areaListLicense";
        public static final String DROPDOWN_AREA_LIST_LICENSEE = "areaListLicense";
        public static final String DROPDOWN_TRADENAME_LIST = "tradeNameList";
        public static final String MESSAGE = "message";
        public static final String GENERATECERTIFICATE = "Generate Certificate";
        public static final String BUTTONAPPROVE = "Approve";
        public static final String BUTTONFORWARD = "Forward";
        public static final String BUTTONREJECT = "Reject";
        public static final String BUTTONSUBMIT = "Submit";
        public static final String BUTTONGENERATEDCERTIFICATE = "GeneratedCertificate";
        public static final String BUTTONPRINTCOMPLETED = "PrintCompleted";
        public static final String WORKFLOW_STATE_NEW = "NEW";
        public static final String WORKFLOW_STATE_REJECTED = "Rejected";
        public static final String WORKFLOW_STATE_COLLECTED = "Fee Collected";
        public static final String WORKFLOW_STATE_GENERATECERTIFICATE = "Generate Certificate";
        public static final String WORKFLOW_STATE_GENERATENOC = "Generate NOC";
        public static final String WORKFLOW_STATE_TYPE_CREATENEWLICENSE = "Create License:";
        public static final String WORKFLOW_STATE_TYPE_RENEWLICENSE = "Renew License:";
        public static final String WORKFLOW_STATE_TYPE_TRANSFERLICENSE = "Transfer License:";
        public static final String WORKFLOW_STATE_GENERATEREJCERTIFICATE = "Generate Rejection Certificate";
        public static final String ACKNOWLEDGEMENT = "acknowledgement";
        public static final String LICENSE_STATUS_ACKNOWLEDGED = "Acknowledged";
        public static final String LICENSE_STATUS_ACTIVE = "Active";
        public static final String LICENSE_STATUS_OBJECTED = "Objected";
        public static final String LICENSE_STATUS_CANCELLED = "Cancelled";
        public static final String LICENSE_STATUS_UNDERWORKFLOW = "UnderWorkflow";
        public static final String STATUS_CANCELLED = "CAN";
        public static final String STATUS_ACTIVE = "ACT";
        public static final String STATUS_ACKNOLEDGED = "ACK";
        public static final String STATUS_UNDERWORKFLOW = "UWF";
        public static final int RENEWALTIMEPERIOD = -90;
        public static final String TRADELICENSE = "TradeLicense";
        public static final String TRADELICENSEMODULE = "TRADELICENSE";
        public static final String CANCELLED = "CANCELLED";
        public static final String TOTAL_LICENSES = "TOTAL_LICENSES";
        public static final String ZONE_ID = "ZONE_ID";
        public static final String WARD_ID = "WARD_ID";
        public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
        public static final String OBJECTED = "OBJECTED";
        public static final String PENDING_RENEWALS = "PENDING_RENEWALS";
        public static final String RENEWED = "RENEWED";
        public static final String WARD = "WARD";
        public static final String WARD_NUM = "WARD_NUM";
        public static final String WARD_NAME = "WARD_NAME";
        public static final String NO_OF_LATE_RENEWALS = "NO_OF_LATE_RENEWALS";
        public static final String TRADE_ID = "TRADE_ID";
        public static final String TRADELICENSE_MODULENAME = "Trade License";
        public static final String TRADELICENSE_LICENSETYPE = "tradelicense";
        public static final String ELECTRICALLICENSE_LICENSETYPE = "electricalcontractorlicense";
        public static final String TOTAL_NEW = "TOTAL_NEW";
        public static final String TOTAL_CAN = "TOTAL_CAN";
        public static final String TOTAL_OBJ = "TOTAL_OBJ";
        public static final String TOTAL_AMT = "TOTAL_AMT";
        public static final String TOTAL_PENDING = "TOTAL_PENDING";
        public static final String TOTAL_RENEWED = "TOTAL_RENEWED";
        public static final String TOTAL_ISSUED = "TOTAL_ISSUED";
        public static final String TOTAL_LATEREN = "TOTAL_LATEREN";
        public static final int AMOUNT_PRECISION_DEFAULT = 2;
        public static final String BEFORE_RENEWAL = "beforeRenew";
        public static final String ACKNOWLEDGEMENT_RENEW = "acknowledgement_renew";
        public static final BigDecimal CHQ_BOUNCE_PENALTY = BigDecimal.valueOf(1000);
        public static final String DEMANDRSN_STR_CHQ_BOUNCE_PENALTY = "CHEQUE BOUNCE PENALTY";
        public static final Character DMD_STATUS_CHEQUE_BOUNCED = 'B';
        public static final String DEMANDRSN_CODE_CHQ_BOUNCE_PENALTY = "CHQ_BUNC_PENALTY";
        public static final String DEMANDRSN_REBATE = "REBATE";
        public static final String TRANSACTIONTYPE_CREATE_LICENSE = "Create License";
        public static final String OWNERSHIP_TYPE_OWN = "Own";
        public static final String OWNERSHIP_TYPE_RENTED = "Rented";
        public static final String OWNERSHIP_TYPE_STATEGOVERNMENT = "State Government";
        public static final String OWNERSHIP_TYPE_CENTRALGOVERNMENT = "Central Government";
        public static final String OWNERSHIP_TYPE_ULB = "ULB";

        public static final Map<String, String> OWNERSHIP_TYPE = new HashMap<String, String>() {

                {
                        put(OWNERSHIP_TYPE_OWN, OWNERSHIP_TYPE_OWN);
                        put(OWNERSHIP_TYPE_RENTED, OWNERSHIP_TYPE_RENTED);
                        put(OWNERSHIP_TYPE_ULB, OWNERSHIP_TYPE_ULB);
                        put(OWNERSHIP_TYPE_STATEGOVERNMENT, OWNERSHIP_TYPE_STATEGOVERNMENT);
                        put(OWNERSHIP_TYPE_CENTRALGOVERNMENT,
                                        OWNERSHIP_TYPE_CENTRALGOVERNMENT);

                }
        };

        public static final String LOCALITY = "locality";
        public static final String LOCATION_HIERARCHY_TYPE = "LOCATION";
        public static final String LICENSE_BILLNO_SEQ = "SEQ_BILLNO_";
        public static final String LICENSE_FEE_TYPE="License Fee";
        public static final String APPLICATION_STATUS_CREATED_CODE = "CREATED";
        public static final String APPLICATION_STATUS_INSPE_CODE = "INSPECTIONDONE";
        public static final String APPLICATION_STATUS_APPROVED_CODE = "APPROVED";
        public static final String APPLICATION_STATUS_COLLECTION_CODE = "COLLECTIONPENDING";
        public static final String APPLICATION_STATUS_DIGUPDATE_CODE = "DIGITALSIGNPENDING";
        public static final String APPLICATION_STATUS_GENECERT_CODE = "CERTIFICATEGENERATED";
        public static final String RENEWAL_LIC_APPTYPE ="Renew";
        public static final String REVENUE_HIERARCHYTYPE = "REVENUE";
        public static final String STR_FOR_EMAILSUBJECT="Trade License application Amount Collected for TIN No.";
        public static final String SEARCH_BY_APPNO = "ApplicationNumber";
        public static final String SEARCH_BY_LICENSENO = "LicenseNumber";
        public static final String SEARCH_BY_OLDLICENSENO = "OldLicenseNumber";
        public static final String SEARCH_BY_TRADETITLE = "TradeTitle";
        public static final String SEARCH_BY_TRADEOWNERNAME = "TradeOwnerName";
        public static final String SEARCH_BY_PROPERTYASSESSMENTNO = "PropertyAssessmentNo";
        public static final String SEARCH_BY_MOBILENO = "MobileNo";
        public static final String TL_APPROVER_ROLENAME = "TLApprover";
        public static final String TL_CREATOR_ROLENAME = "TLCreator";
        public static final String REVENUE_HIERARCHY_TYPE = "REVENUE";
        public static final String BLOCK = "Block";
        public static final String PENALTY_DMD_REASON_CODE = "Penalty";
        public static final String APPROVAL_COMMENT = "approvalComment";
        public static final String SIGNWORKFLOWACTION = "Sign";
        public static final String WF_PREVIEW_BUTTON = "Preview";
        public static final String APPLICATION_NUMBER = "applicationNumber";
        public static final String FILE_STORE_ID_APPLICATION_NUMBER = "fileStoreIdApplicationNumber";
        public static final String DIGITALSIGNINCLUDEINWORKFLOW =  "DIGITALSIGNINCLUDEINWORKFLOW";
        public static final String SIGNED_DOCUMENT_PREFIX = "SN/";
        public static final String ROLE_COMMISSIONERDEPARTEMNT ="Health";
        public static final String PERMANENT_NATUREOFBUSINESS = "Permanent";
}

