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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row" id="page-content">
	<div class="col-md-12">
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert">
				<spring:message code="${message}" />
			</div>
		</c:if>

		<form:form class="form-horizontal form-groups-bordered"	id="baseregisterform" modelAttribute="currInstDCBReport" method="post">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<spring:message code="lbl.hdr.currInstDCB" />
					</div>
				</div>
				<div class="panel-body custom-form">
					<div class="form-group">
						<label for="field-1" class="col-sm-3 control-label"><spring:message code="lbl.ward" /></label>
						<div class="col-sm-3 add-margin">
							<form:select name="ward" id="ward" path=""
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.default.all" />
								</form:option>
								<form:options items="${wards}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>

					</div>
				</div>
				</div>
				<div class="row">
	       			<div class="text-center">
	       				<button type="button" class="btn btn-primary" id="currInstDCBReportSearch"><spring:message code="lbl.search"/></button>
	          		    <a href="javascript:void(0)" class="btn btn-default" data-dismiss="modal" onclick="self.close()"><spring:message code="lbl.close"/></a>
	          		</div>
        		</div>
		</form:form>
		<div></div>
		<br />
		<spring:message code="reports.note.text" />
		<table class="table table-bordered table-hover multiheadertbl" id="currInstDCBReport-table">
		
		<tfoot id="report-footer">
							<tr>
								<td>Total</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							 	<td></td> 
							 	</tr></tfoot>
		</table>
	</div>
</div>

<link rel="stylesheet" href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>"/>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egi'/>"></script>
<script	src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"
	type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/resources/js/app/currInstDCBReport.js?rnd=${app_release_no}'/>"></script>
