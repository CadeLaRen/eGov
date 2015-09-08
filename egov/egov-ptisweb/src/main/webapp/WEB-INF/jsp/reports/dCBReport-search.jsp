<!--
	eGov suite of products aim to improve the internal efficiency,transparency, 
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
-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title><s:text name='dcbreport.search' /></title>
</head>
<body>
	<div id="dcbError" class="errorstyle" style="display:none;"></div>
	<div class="row">
		<div class="col-md-12">
			<div class="panel-body">
				<s:form name="dcbForm" action="dCBReport" theme="simple"
					cssClass="form-horizontal form-groups-bordered">
					<div class="panel panel-primary" data-collapsed="0">
						<div class="panel-heading">
							<div class="panel-title text-left">DCB Drill Down Report</div>
						</div>
						<div class="panel-body custom-form">
						<s:hidden id="mode" name="mode" value="%{mode}"/> 
						<s:hidden id="boundaryId" name="boundaryId" value="%{boundaryId}"/> 
						<s:hidden id="selectedModeBndry" name="selectedModeBndry" value="%{selectedModeBndry}"/> 
							<div class="form-group">
								<label for="field-1" class="col-sm-3 control-label text-right"><s:text
										name="Zone" /> :</label>
								<div class="col-sm-6 add-margin">
									<s:select headerKey="-1"
										headerValue="%{getText('default.select')}" name="zoneId"
										id="zoneId" listKey="key" listValue="value"
										list="ZoneBndryMap" cssClass="form-control" value="%{zoneId}" />
								</div>
							</div>
						</div>
					</div>
				</s:form>

				<div class="row">
					<div class="text-center">
						<button type="button" id="btnsearch" class="btn btn-success">
							Search</button>
						<button type="button" id="btnclose" class="btn btn-default" onclick="window.close();">
							Close</button>
					</div>
				</div>
			</div>

			<div class="row display-hide report-section">
				<div class="col-md-12 table-header text-left">DCB Drill Down Report Details</div>
				<div class="col-md-12 form-group report-table-container">
					<table class="table table-bordered table-hover multiheadertbl" id="tbldcbdrilldown">
						<thead>
                            <tr>
                             <th rowspan="2">Name</th>
                             <th colspan="7">Demand</th>
                             <th colspan="9">Collection</th>
                             <th colspan="3">Balance</th>
                            </tr>

							<tr>
								<th>Arrear
									Property Tax</th>
								<th>Arrear
									LibraryCess</th>
								<th>Arrear
									Total</th>
								<th>Current
									Property Tax</th>
								<th>Current
									LibraryCess</th>
								<th>Current Total</th>
								<th>Total
									Demand</th>
								<th>Arrear
									Property Tax</th>
								<th>Arrear
									LibraryCess</th>
								<th>Penalty
									On Arrear</th>
								<th>Arrear
									Total</th>
								<th>Current
									Property Tax</th>
								<th>Current
									LibraryCess</th>
								<th>Position</th>
								<th>Current
									Total</th>
								<th>Total
									Collection</th>
								<th>Arrear
									Property Tax</th>
								<th>Current Property Tax</th>
								<th>Total PropertyTax Balance</th>
							</tr>
						</thead>
						 <tfoot id="report-footer">
							<tr>
								<td>Total</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tfoot> 
					</table>
				</div>
			</div>

			<div id="report-backbutton" class="col-xs-12 text-center">
				<div class="form-group"> <buttton class="btn btn-primary" id="backButton" > Back</buttton></div>
			</div>
		</div>
	</div>
<link rel="stylesheet" href="<c:url value='/resources/global/css/font-icons/entypo/css/entypo.css' context='/egi'/>">
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.columnFilter.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egi'/>"></script>
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"
	type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/dCBReport.js'/>"></script>
</html>