<!-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#     accountability and the service delivery of the government  organizations.
#  
#      Copyright (C) <2015>  eGovernments Foundation
#  
#      The updated version of eGov suite of products as by eGovernments Foundation 
#      is available at http://www.egovernments.org
#  
#      This program is free software: you can redistribute it and/or modify
#      it under the terms of the GNU General Public License as published by
#      the Free Software Foundation, either version 3 of the License, or
#      any later version.
#  
#      This program is distributed in the hope that it will be useful,
#      but WITHOUT ANY WARRANTY; without even the implied warranty of
#      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#      GNU General Public License for more details.
#  
#      You should have received a copy of the GNU General Public License
#      along with this program. If not, see http://www.gnu.org/licenses/ or 
#      http://www.gnu.org/licenses/gpl.html .
#  
#      In addition to the terms of the GPL license to be adhered to in using this
#      program, the following additional terms are to be complied with:
#  
#  	1) All versions of this program, verbatim or modified must carry this 
#  	   Legal Notice.
#  
#  	2) Any misrepresentation of the origin of the material is prohibited. It 
#  	   is required that all modified versions of this material be marked in 
#  	   reasonable ways as different from the original version.
#  
#  	3) This license does not grant any rights to any user of the program 
#  	   with regards to rights under trademark law for use of the trade names 
#  	   or trademarks of eGovernments Foundation.
#  
#    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------->
<%@ include file="/includes/taglibs.jsp"%>
<html>
	<head>
		<title><s:text name="license.search" /></title>
		<link href="/resources/css/license/searchTrade.css" rel="stylesheet" type="text/css"></link>
		<script type="text/javascript" src="/resources/javascript/license/searchTrade.js"></script>
	</head>
	<body onload="init()">
		<table align="center" width="100%">
			<tbody>
				<tr>
					<td>
						<div align="center">
							<center>
								<div class="formmainbox">
									<div class="headingbg">
										<s:text name="license.search" />
									</div>
									<s:push value="model">
										<s:form action="searchTrade-search" theme="simple" name="searchForm">
											<s:hidden name="actionName" value="search" />
											<div id="error" style="color: #FF0000">
											</div>
											<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
												<tbody>
													<%@ include file='searchTrade-form.jsp'%>
												</tbody>
											</table>
											<div class="buttonbottom">
												<s:submit name="button32" onclick="return validateForm()" cssClass="buttonsubmit" id="button32" method="search" value="Search" />
												<s:reset name="button" cssClass="button" id="button" value="Reset" />
												<input name="button2" type="button" class="button" id="button" onclick="window.close()" value="Close" />
											</div>
											<%@ include file='searchTrade-result.jsp'%>
										</s:form>
									</s:push>
								</div>
							</center>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>
