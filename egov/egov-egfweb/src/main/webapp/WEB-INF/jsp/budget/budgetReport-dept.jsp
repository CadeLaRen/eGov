<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>


<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java"%>
<%@ page import="org.egov.budget.model.*"%>
<jsp:include page="budgetHeader.jsp">
	<jsp:param name="heading" value="Budget Report - Departmentwise" />
</jsp:include>
<s:actionmessage theme="simple" />
<s:actionerror />
<s:fielderror />
<script>
	function popUp(url) {
		newwindow=window.open(url,'name','height=200,width=150');
		if (window.focus) {newwindow.focus()}
		return false;
	}
</script>
<s:actionmessage theme="simple" />
<s:form name="budgetDetailReportForm" action="budgetReport" id = "budgetDetailReportForm"
	theme="simple">
	<div class="formmainbox">
		<div class="subheadnew">Budget Report - Departmentwise</div>
		<%@include file="budgetReport-form.jsp"%>
		<div class="buttonbottom" style="padding-bottom: 10px;">
			<s:submit value="PRINT " onclick="submitForm('printDepartmentWiseReport')"
				cssClass="buttonsubmit" />
			<s:submit value="SAVE AS PDF" onclick="submitForm('generateDepartmentWisePdf')"
				cssClass="buttonsubmit" />
			<s:submit value="SAVE AS EXCEL" onclick="submitForm('generateDepartmentWiseXls')"
				cssClass="buttonsubmit" />
		</div>
	</div>
</s:form>
<script>
function submitForm(method){
	document.budgetDetailReportForm.action = "/EGF/budget/budgetReport-"+method+".action";
	document.budgetDetailReportForm.submit();
}
	document.getElementById('function').style.display="none";
	document.getElementById('function_label').style.visibility="hidden";
</script>
