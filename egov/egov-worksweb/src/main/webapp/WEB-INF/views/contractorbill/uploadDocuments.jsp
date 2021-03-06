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

<%@ include file="/includes/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="<egov:url path='resources/js/documentsupload.js?rnd=${app_release_no}'/>"></script>
<style>
	.file-ellipsis {
		width : auto !Important;
	}
	
	.padding-10
	{
	  padding:10px;
	}
	
	.margin-10
	{
	  margin:10px;
	}
	
</style>


<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
			<c:if test="${mode != 'view' && mode != 'readOnly' }">
				<spring:message code="lbl.upload.document" />
			</c:if>
			<c:if test="${mode == 'view' || mode == 'readOnly' }">
				<spring:message code="lbl.documents" />
			</c:if>
	</div>
</div>

<div style="padding: 0 15px;">

	<c:if test="${contractorBillRegister.documentDetails != null &&  !contractorBillRegister.documentDetails.isEmpty()}">
		<c:forEach items="${contractorBillRegister.documentDetails }" var="documentDetials">
			<a href="/egi/downloadfile?fileStoreId=${documentDetials.fileStore.fileStoreId}&moduleName=WMS">${documentDetials.fileStore.fileName }</a><br />
		</c:forEach>
	</c:if>
	<c:if test="${mode == 'view' && contractorBillRegister.documentDetails.isEmpty()}">
		<spring:message code="msg.no.documents" />
	</c:if>
	<input type="hidden" value="${fn:length(contractorBillRegister.documentDetails)}" id="documentsSize">
	<c:if test="${mode != 'view' && mode != 'readOnly' }">
		<div>
			<table width="100%">
				<c:if test="${contractorBillRegister.documentDetails != null &&  fn:length(contractorBillRegister.documentDetails) lt 4}">
				<tbody>
					<tr>
						<td valign="top">
						 	<table id="uploadertbl" width="100%"><tbody>
						 		<tr id="row1">			 				
									<td>
										<input type="file" name="file" id="file1" onchange="isValidFile(this.id)" class="padding-10">
									</td>
								</tr>									 										
						 	</tbody></table>
						</td>
					</tr>
					<tr>
						<td align="left">
							<button id="attachNewFileBtn" type="button" class="btn btn-primary margin-10" onclick="addFileInputField()"><spring:message code="lbl.addfile" /></button>
						</td>
					</tr>
				</tbody>
				</c:if>
			</table>
		</div>
	</c:if>
</div>
