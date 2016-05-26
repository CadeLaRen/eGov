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
package org.egov.stms.utils.constants;

import java.util.LinkedHashMap;

public class SewerageTaxConstants {

    public static final String FILESTORE_MODULECODE = "STMS";
    public static final String MODULE_NAME = "Sewerage Tax Management";
    public static final String APPL_INDEX_MODULE_NAME = "Sewerage Tax";
    public static final String APPLICATION_NUMBER = "applicationNumber";
    public static final String NEWSEWERAGECONNECTION = "NEWSEWERAGECONNECTION";
    public static final String MODULETYPE = "SEWERAGETAXAPPLICATION";
    public static final String REVENUE_WARD = "WARD";

    // appconfig keys
    public static final String NEWCONNECTIONALLOWEDIFPTDUE = "NEWCONNECTIONALLOWEDIFPTDUE";

    // application constants
    public static final String APPLICATION_STATUS_CREATED = "CREATED";
    public static final String APPLICATION_STATUS_CHECKED = "CHECKED";
    public static final String APPLICATION_STATUS_APPROVED = "APPROVED";
    public static final String APPLICATION_STATUS_VERIFIED = "VERIFIED";
    public static final String APPLICATION_STATUS_ESTIMATENOTICEGEN = "ESTIMATIONNOTICEGENERATED";
    public static final String APPLICATION_STATUS_FEEPAID = "ESTIMATIONAMOUNTPAID";
    public static final String APPLICATION_STATUS_WOGENERATED = "WORKORDERGENERATED";
    public static final String APPLICATION_STATUS_CANCELLED = "CANCELLED";
    public static final String APPLICATION_STATUS_SANCTIONED = "SANCTIONED";
    public static final String APPLICATION_STATUS_CLERKAPPROVED = "Clerk approved";

    public static final String APPLICATION_STATUS_INSPECTIONFEEPAID = "INSPECTIONFEEPAID";
    public static final String APPLICATION_STATUS_INITIALAPPROVED = "INITIALAPPROVED";
    public static final String APPLICATION_STATUS_FIELDINSPECTED = "FIELD INSPECTED";
    public static final String APPLICATION_STATUS_REJECTED = "REJECTED";

    public static final String CLERKDESIGNATIONFORCSCOPERATOR = "CLERKDESIGNATIONFORCSCOPERATOR";
    public static final String SEWERAGETAXWORKFLOWDEPARTEMENT = "DEPARTMENTFORWORKFLOW";

    public static final String WORKFLOWTYPE_DISPLAYNAME = "Sewerage Connection";

    public static final String WF_STATE_REJECTED = "Rejected";
    public static final String WFLOW_ACTION_STEP_REJECT = "Reject";

    public static final String WF_STATE_CLERK_APPROVED = "Clerk approved";
    public static final String WF_STATE_DEPUTY_EXE_APPROVED = "Deputy executive engineer approved";
    public static final String WF_STATE_ASSISTANT_APPROVED = "Assistant Engineer approved";

    // designations
    public static final String DESIGNATION_DEPUTY_EXE_ENGINEER = "deputy executive engineer";
    public static final String DESIGNATION_EXE_ENGINEER = "executive engineer";

    // User roles
    public static final String ROLE_SUPERUSER = "Super User";
    public static final String ROLE_EXECUTIVEDEPARTEMNT = "Engineering";
    public static final String ROLE_DEPUTYDEPARTEMNT = "Engineering";
    public static final String ROLE_CSCOPERTAOR = "CSC Operator";
    public static final String ROLE_ULBOPERATOR = "ULB Operator";
    public static final String ROLE_BILLCOLLECTOR = "Collection Operator";
    public static final String ROLE_CITIZEN = "Citizen";

    public static final String MODE = "mode";
    public static final String APPROVAL_POSITION = "approvalPosition";
    public static final String APPROVAL_COMMENT = "approvalComment";
    public static final String WORKFLOW_ACTION = "workFlowAction";

    public static final String SUBMITWORKFLOWACTION = "Submit";
    public static final String APPROVEWORKFLOWACTION = "Approve";

    public static final String WF_ESTIMATION_NOTICE_BUTTON = "Generate Estimation Notice";
    public static final String WF_STATE_CONNECTION_EXECUTION_BUTTON = "Execute Connection";
    public static final String WF_CLOSERACKNOWLDGEENT_BUTTON = "Generate Acknowledgement";
    public static final String WF_WORKORDER_BUTTON = "Generate Work Order";

    public static final String PREVIEWWORKFLOWACTION = "Preview";

    public static final String VIEW = "View";
    public static final String COLLECTDONATIONCHARHGES = "Collect Fee";
    public static final String VIEWURL = "/stms/existing/sewerage/view/{consumerno}/{assessmentno}";
    public static final String COLLECTDONATIONCHARHGESURL = "/stms/collection/generatebill/{consumerno}/{assessmentno}";

    // Elastic Search Constants
    public static final String SEARCHABLE_DHSCNO = "searchable.dhscnumber";
    public static final String CLAUSES_CITYNAME = "clauses.cityname";
    public static final String SEARCHABLE_CONSUMER_NAME = "searchable.consumername";
    public static final String CLAUSES_MOBILENO = "clauses.mobilenumber";
    public static final String CLAUSES_DOORNO = "clauses.doorno";
    public static final String CLAUSES_REVWARD_NAME = "clauses.revwardname";
    public static final String CLAUSES_APPLICATION_DATE = "clauses.applicationdate";

    public static final String FEE_INSPECTIONCHARGE = "INSPECTIONCHARGE";

    public static final LinkedHashMap<Integer, Integer> PIPE_SCREW_SIZE = new LinkedHashMap<Integer, Integer>() {
        /**
         *
         */
        private static final long serialVersionUID = -1063445500884125741L;

        {
            put(1, 1);
            put(2, 2);
            put(3, 3);
            put(4, 4);
            put(5, 5);
            put(6, 6);
            put(7, 7);
            put(8, 8);
            put(9, 9);
            put(10, 10);
            put(11, 11);
            put(12, 12);
            put(13, 13);
            put(14, 14);
            put(15, 15);
        }
    };
    public static final String FEES_ESTIMATIONCHARGES_CODE = "ESTIMATIONCHARGE";
    public static final String APPLICATION_STATUS_COLLECTINSPECTIONFEE = "COLLECTINSPECTIONFEE";
    public static final String SEWAREGE_FUCNTION_CODE = "SEWERAGE_FUNCTION_CODE";
    public static final String APPCONFIG_COLLECT_INSPECTIONFEE = "SEWERAGE_COLLECTINSPECTION_FEE";

    public static final String COLL_RECEIPTDETAIL_DESC_PREFIX = "Collection";
    public static final String BILL_TYPE_AUTO = "AUTO";
    public static final String STRING_DEPARTMENT_CODE = "REV";
    public static final String STRING_SERVICE_CODE = "STAX";
    public static final String EST_STRING_SERVICE_CODE = "SWT-EST";
    public static final String DEFAULT_FUNCTIONARY_CODE = "1";
    public static final String DEFAULT_FUND_SRC_CODE = "01";
    public static final String DEFAULT_FUND_CODE = "01";
    public static final String DISPLAY_MESSAGE = "Sewerage Tax Collection";

    public static final String FEES_DONATIONCHARGE_CODE = "DONATIONCHARGE";
}
