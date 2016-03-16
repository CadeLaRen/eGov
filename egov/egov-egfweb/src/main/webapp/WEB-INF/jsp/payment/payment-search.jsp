<!--  #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#      accountability and the service delivery of the government  organizations.
#   
#       Copyright (C) <2015>  eGovernments Foundation
#   
#       The updated version of eGov suite of products as by eGovernments Foundation 
#       is available at http://www.egovernments.org
#   
#       This program is free software: you can redistribute it and/or modify
#       it under the terms of the GNU General Public License as published by
#       the Free Software Foundation, either version 3 of the License, or
#       any later version.
#   
#       This program is distributed in the hope that it will be useful,
#       but WITHOUT ANY WARRANTY; without even the implied warranty of
#       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#       GNU General Public License for more details.
#   
#       You should have received a copy of the GNU General Public License
#       along with this program. If not, see http://www.gnu.org/licenses/ or 
#       http://www.gnu.org/licenses/gpl.html .
#   
#       In addition to the terms of the GPL license to be adhered to in using this
#       program, the following additional terms are to be complied with:
#   
#   	1) All versions of this program, verbatim or modified must carry this 
#   	   Legal Notice.
#   
#   	2) Any misrepresentation of the origin of the material is prohibited. It 
#   	   is required that all modified versions of this material be marked in 
#   	   reasonable ways as different from the original version.
#   
#   	3) This license does not grant any rights to any user of the program 
#   	   with regards to rights under trademark law for use of the trade names 
#   	   or trademarks of eGovernments Foundation.
#   
#     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#-------------------------------------------------------------------------------  -->
<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java"%>

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/EGF/resources/css/ccMenu.css" />
<s:if
	test="%{disableExpenditureType == true && enablePensionType == false}">
	<title>Salary Bill Payment Search</title>
</s:if>
<s:elseif
	test="%{disableExpenditureType == true && enablePensionType == true}">
	<title>Pension Bill Payment Search</title>
</s:elseif>
<s:else>
	<title>Bill Payment Search</title>
</s:else>
</head>
<body>
	<s:form action="payment" theme="simple">
		<jsp:include page="../budget/budgetHeader.jsp">
			<jsp:param name="heading" value="Bill Payment Search" />
		</jsp:include>
		<span class="mandatory1" id="errorSpan"> <s:actionerror /> <s:fielderror />
			<s:actionmessage />
		</span>
		<div class="formmainbox">
			<div class="subheadnew">
				<s:if
					test="%{disableExpenditureType == true && enablePensionType == false}">Salary Bill Payment Search</s:if>
				<s:elseif
					test="%{disableExpenditureType == true && enablePensionType == true}">Pension Bill Payment Search</s:elseif>
				<s:else>Bill Payment Search</s:else>
			</div>
			<table align="center" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="bluebox"></td>
					<td class="bluebox"><s:text name="payment.billnumber" /></td>
					<td class="bluebox"><s:textfield name="billNumber"
							id="billNumber" maxlength="25" value="%{billNumber}" /></td>
					<td class="bluebox"></td>
					<td class="bluebox"></td>
				</tr>
				<tr>
					<td class="bluebox"></td>
					<td class="greybox"><s:text name="payment.billdatefrom" /></td>
					<td class="greybox"><s:textfield name="fromDate" id="fromDate"
							maxlength="20" value="%{fromDate}"
							onkeyup="DateFormat(this,this.value,event,false,'3')" /><a
						href="javascript:show_calendar('forms[0].fromDate');"
						style="text-decoration: none">&nbsp;<img
							src="/egi/resources/erp2/images/calendaricon.gif" border="0" /></a><br />(dd/mm/yyyy)</td>
					<td class="greybox"><s:text name="payment.billdateto" /></td>
					<td class="greybox"><s:textfield name="toDate" id="toDate"
							maxlength="20" value="%{toDate}"
							onkeyup="DateFormat(this,this.value,event,false,'3')" /><a
						href="javascript:show_calendar('forms[0].toDate');"
						style="text-decoration: none">&nbsp;<img
							src="/egi/resources/erp2/images/calendaricon.gif" border="0" /></a>(dd/mm/yyyy)</td>
				</tr>
				<tr>
					<td class="bluebox"></td>
					<td class="bluebox"><s:text name="payment.expendituretype" />
					</td>
					<td class="bluebox"><s:select name="expType" id="expType"
							list="#{'-1':'----Choose----','Purchase':'Purchase','Works':'Works','Expense':'Expense'}"
							value="%{expType}" /></td>
					<td class="bluebox"></td>
					<td class="bluebox"></td>

				</tr>
				<jsp:include page="../voucher/vouchertrans-filter.jsp" />
			</table>

		</div>
		<%-- 	<s:if test="%{!validateUser('createpayment')}">
		<script>
			document.getElementById('searchBtn').disabled=true;
			document.getElementById('errorSpan').innerHTML='<s:text name="payment.invalid.user"/>';
			if(document.getElementById('vouchermis.departmentid'))
			{
				var d = document.getElementById('vouchermis.departmentid');
				d.options[d.selectedIndex].text='----Choose----';
				d.options[d.selectedIndex].text.value=-1;
			}
		</script>
		</s:if> --%>
		<%-- <s:if test="%{validateUser('deptcheck')}">
			<script>
				if(document.getElementById('vouchermis.departmentid'))
				{
					document.getElementById('vouchermis.departmentid').disabled=true;
				}
			</script>
		</s:if> --%>
		<s:hidden name="disableExpenditureType" id="disableExpenditureType"
			value="%{disableExpenditureType}" />
		<s:hidden name="enablePensionType" id="enablePensionType"
			value="%{enablePensionType}" />
	</s:form>
	<div align="center" class="buttonbottom">
		<input type="submit" class="buttonsubmit" value="Search"
			id="searchBtn" name="searchBtn" onclick="return search();" /> <input
			type="button" value="Close" onclick="javascript:window.close()"
			class="button" />
	</div>
	<script>
			function loadBank(obj){}
			function search()
			{
				document.forms[0].action='${pageContext.request.contextPath}/payment/payment-search.action';
	    		document.forms[0].submit();
				return true;
			}
			<s:if test="%{disableExpenditureType == true && enablePensionType == false}">
				var element = document.getElementById('expType');
				var len = element.options.length;
				element.options.length = 0;
				element.options[element.length] = new Option('Salary', 'Salary');
				element.disabled = true;
			</s:if>
			<s:if test="%{disableExpenditureType == true && enablePensionType == true}">
				var element = document.getElementById('expType');
				var len = element.options.length;
				element.options.length = 0;
				element.options[element.length] = new Option('Pension', 'Pension');
				element.disabled = true;
			</s:if>
		</script>
</body>
</html>