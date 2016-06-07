<!--
	eGov suite of products aim to improve the internal efficiency,transparency, 
    accountability and the service delivery of the government  organizations.
 
    Copyright (C) <2016>  eGovernments Foundation
 
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<form:form method="post" action=""
	class="form-horizontal form-groups-bordered"
	modelAttribute="donationMaster" id="donationMasterUpdateform">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${not empty message}">
				<div class="alert alert-success" role="alert">
					<spring:message code="${message}" />
				</div>
			</c:if>
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">
						<strong><spring:message code="lbl.swtax.donationDetails"></spring:message></strong>
					</div>
				</div>
				<div class="panel-body">
					<div class="row add-border">
						<div class="col-md-3 col-xs-6 add-margin">
							<spring:message code="lbl.propertytype" />
						</div>
						<div id="propertyType" class="col-md-3 col-xs-6 add-margin view-content">
							<c:out value="${donationMaster.propertyType}" />
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row add-border">
						<div class="col-md-3 col-xs-6 add-margin">
							<spring:message code="lbl.effective.fromdate" />
						</div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
							<fmt:formatDate pattern="dd/MM/yyyy" value="${donationMaster.fromDate}" />
						</div>
							
					</div>
				</div>
			</div>
			<div>
				<table class="table table-bordered" id="donationMasterViewTable">
					<thead>
						<tr>
							<th class="text-center"><spring:message code="lbl.noofclosets"/></th>
							<th class="text-right"><spring:message code="lbl.donation.amount" /></th>	
							<th class="text-left"><spring:message code="lbl.actions"/></th>
						</tr>	
					</thead>
					<tbody>
						<c:forEach var="donationRates" items="${donationMaster.donationDetail}" varStatus="status" >
							<tr>
								<td>
									<input type="text" class="form-control patternvalidation donationRatesNoOfClosets" 
									style="text-align: center; font-size: 12px;" data-pattern="number" 
									id="donationDetail[${status.index}].noOfClosets" 
									value="${donationRates.noOfClosets}"  name="donationDetail[${status.index}].noOfClosets" 
									maxlength="8" required="required" />
								</td>
								<td>
									<input type="text" class="form-control patternvalidation donationRatesAmount" 
									id="donationDetail[${status.index}].amount"  
									style="text-align: right; font-size: 12px;" value="${donationRates.amount}" data-pattern="decimalvalue" 
									name="donationDetail[${status.index}].amount"  maxlength="8" required="required" />
								</td>
								<form:hidden path="donationDetail[${status.index}].id" id="id" value="${donationRates.id}"></form:hidden>
								<td>
									<button type="button" onclick="deleteCurrentRow(this)" id="Add" 
									class="btn btn-primary display-hide delete-button"><spring:message code="lbl.swtax.deleteRow" /></button>
								</td>
							</tr>
						</c:forEach>
						<script type="text/javascript">
								$( "#donationMasterViewTable tr:last .delete-button").show();
						</script>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class=" text-center">
			<button type="button" id="btn-addNewRow" class="btn btn-primary btn-addNewRow"><spring:message code="lbl.swtax.addRow"></spring:message></button>
			<input type="submit" class="btn btn-primary" id="submitDonationValues" />
			<a href="javascript:void(0)" class="btn btn-default"
				onclick="self.close()"><spring:message code="lbl.close" /></a>
	</div>
</form:form>

<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egi'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/datatables/moment.min.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/jquery/plugins/datatables/datetime-moment.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/js/masters/donationMaster.js?rnd=${app_release_no}'/>"></script>
