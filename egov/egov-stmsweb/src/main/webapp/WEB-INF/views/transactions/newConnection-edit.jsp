<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form role="form" method="post" modelAttribute="sewerageApplicationDetails" id="editSewerageApplicationDetailsForm" cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">				
<div class="page-container" id="page-container">
	<form:hidden id="mode" path="" name="mode" value="${mode}"/> 
	<form:hidden path="" id="approvalPositionExist" value="${approvalPositionExist}"/>
	<form:hidden path="" id="statuscode" value="${sewerageApplicationDetails.status.code}"/>
	<form:hidden path="" id="wfstate" value="${sewerageApplicationDetails.state.id}"/> 
	<input type="hidden" id="currentUser" value="${currentUser}"/>  
	<input type="hidden" id="sewerageTaxDue" value="${sewerageTaxDue}" name="sewerageTaxDue"/>  
	<form:hidden path="" id="workFlowAction" name="workFlowAction"/>
	<form:hidden path="applicationType" id="applicationType" value="${sewerageApplicationDetails.applicationType.id}"/>
	<form:hidden path="connection.connectionStatus" id="connection.connectionStatus" value="${sewerageApplicationDetails.connection.connectionStatus}"/>
	<%-- <div class="panel panel-primary" data-collapsed="0">
		<div class="panel-heading">
			<div class="panel-title">
				<spring:message  code="lbl.basicdetails"/>
			</div>
		</div>
		<jsp:include page="commonappdetails-view.jsp"></jsp:include>
	</div> --%>
	<div class="panel panel-primary" data-collapsed="0">
		<div class="panel-heading">
			<div class="panel-title">
				<spring:message code="lbl.applicant.details" />
			</div>
		</div>
		<div class="panel-body custom-form ">
			<%-- <c:if test="${sewerageApplicationDetails.status.code =='CREATED' && mode=='fieldInspection'}">
				<%-- <jsp:include page="applicantdetails.jsp"></jsp:include> --%>
				<jsp:include page="commonApplicationDetails-view.jsp"></jsp:include>
			<%-- </c:if> --%>
			<%-- <jsp:include page="connectiondetails.jsp"></jsp:include> --%>	
			<%-- <jsp:include page="documentdetails.jsp"></jsp:include>	 --%>
		</div>
	</div>
	<c:if test="${sewerageApplicationDetails.status.code != 'CREATED'}">
		<jsp:include page="connectionDetails-view.jsp"></jsp:include>
	</c:if>
	<c:if test="${sewerageApplicationDetails.status.code =='CREATED' && mode=='fieldInspection'}">
		<div class="panel panel-primary" data-collapsed="0">
			<jsp:include page="connectiondetails.jsp"></jsp:include> 	
		</div>
		<%-- <jsp:include page="documentdetails-view.jsp"></jsp:include> --%> 
		<jsp:include page="estimationdetails.jsp"></jsp:include>
	</c:if>				
	<c:if test="${sewerageApplicationDetails.status.code =='CREATED' && mode=='edit' }">
		<div class="panel panel-primary" data-collapsed="0">
			<jsp:include page="connectiondetails.jsp"></jsp:include> 	
		</div>
		<%-- <jsp:include page="documentdetails-view.jsp"></jsp:include> --%> 
			</c:if>
	
	<c:if test="${sewerageApplicationDetails.status.code =='CREATED' && mode=='' && sewerageApplicationDetails.state.value =='Rejected'}">
		<div class="panel panel-primary" data-collapsed="0">
			<jsp:include page="connectiondetails.jsp"></jsp:include> 	
		</div>
		<%-- <jsp:include page="documentdetails-view.jsp"></jsp:include> --%> 
	</c:if>			
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading">
				<div class="panel-title">
					<spring:message  code="lbl.apphistory"/>
				</div>
			</div>
			<jsp:include page="applicationhistory-view.jsp"></jsp:include>
		</div>	
		<%-- <c:if test="${sewerageApplicationDetails.status.code == 'ESTIMATIONAMOUNTPAID'}">
		<jsp:include page="sanctiondetails.jsp"></jsp:include>
		</c:if>	 --%>
		<c:if test="${sewerageApplicationDetails.status.code == 'WORKORDERGENERATED'}">
			<jsp:include page="connectionexecutiondetails-form.jsp"></jsp:include>
		</c:if>
		
		<c:if test="${(sewerageApplicationDetails.status.code =='CLOSERINITIATED'  ||   sewerageApplicationDetails.status.code =='CLOSERINPROGRESS'||sewerageApplicationDetails.status.code =='CLOSERSANCTIONED') }">
		<%-- 	<jsp:include page="closerForm-details.jsp"></jsp:include> --%>
			<%-- <jsp:include page="closuredocumentdetails-view.jsp"></jsp:include> --%>
		</c:if>
			
	 	<jsp:include page="../common/commonWorkflowMatrix.jsp"/>
	 	<jsp:include page="../common/commonWorkflowMatrix-button.jsp"/>
</div>	
</form:form>
<script src="<c:url value='/resources/js/transactions/applicationsuccess.js?rnd=${app_release_no}'/>"></script>
<script src="<c:url value='/resources/js/transactions/newconnectionupdate.js?rnd=${app_release_no}'/>"></script>
<script src="<c:url value='/resources/global/js/egov/inbox.js' context='/egi'/>"></script>