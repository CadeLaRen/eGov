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
<%@ page language="java" pageEncoding="UTF-8"%>


<html>
<head>
	<title><s:text name="objectionView.det"></s:text></title>
		<link href="<c:url value='/resources/css/headertab.css'/>" rel="stylesheet" type="text/css" />
	</head>
	<body class="yui-skin-sam">
	<s:push value="model">
			<jsp:include page="objectionDetailsCommonView.jsp"/>
	  <div class="buttonbottom" align="center">
	  	<table>
		<tr>
					<s:if test="egwStatus.code.equalsIgnoreCase(@org.egov.ptis.constants.PropertyTaxConstants@OBJECTION_ACCEPTED)">
					<td>
					<input type="button" name="memoButton" id="memoButton" value="Generate Memo" class="button" 
					onclick="window.open('${pageContext.request.contextPath}/objection/memoGeneration-print.action?objection.id=<s:property value="%{id}"/>','dataitem','resizable=yes,scrollbars=yes,height=700,width=800,status=yes');"/>
					</s:if>
					</td>
					<td><input type="button" name="printButton" id="printButton" value="Print" class="button" onclick="window.print();"/></td>
		    		<td><input type="button" name="closeButton" id="closeButton" value="Close" class="button" onclick="window.close();"/></td>
		</tr>             
		</table></div>    
	</s:push>  
</body>
</html>
