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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
	<div class="col-md-12">
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert"><spring:message code="${message}"/></div>
		</c:if>
		<form:form id="hoardingform" method="post" class="form-horizontal form-groups-bordered" 
		modelAttribute="advertisementPermitDetail" commandName="advertisementPermitDetail" enctype="multipart/form-data">
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading">
				<ul class="nav nav-tabs" id="settingstab">
					<li class="active"><a data-toggle="tab"
						href="#hoardingdetails" data-tabidx="0" aria-expanded="false"><spring:message code="lbl.hoarding.details"/></a></li>
					<li class=""><a data-toggle="tab" href="#hoardingattachments"
						data-tabidx="1" aria-expanded="false"><spring:message code="lbl.hoarding.enclosure"/></a></li>
				</ul>
			</div>
			<div class="panel-body custom-form">
				<div class="tab-content">
					<div class="tab-pane fade active in" id="hoardingdetails">
		
				<jsp:include page="createHoarding.jsp"></jsp:include>
				
							<div class="form-group">
								<label class="col-sm-3 control-label text-right">
								<spring:message code="lbl.pendingtax"/>
									<span class="mandatory"></span>
								</label>
								<div class="col-sm-3 add-margin">
									<form:input type="text" class="form-control patternvalidation" data-pattern="decimalvalue"  maxlength="15"  path="pendingTax" id="pendingTax" required="required"/>
                               		<form:errors path="pendingTax" cssClass="error-msg" />
								</div>
							</div>
			</div>
			</div>

		</div>					
		<div class="text-center">
			<button type="submit" class="btn btn-primary"><spring:message code="lbl.submit"/></button>
			<button type="reset" class="btn btn-default"><spring:message code="lbl.reset"/></button>
		    <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
		</div>
	</form:form>
	</div>
</div>
<script>
//this is to reset the sub combobox upon field error
var subcategory = '${advertisement.subCategory.id}';
var adminBoundry = '${advertisement.ward.id}';
var revenueBoundary = '${advertisement.locality.id}';
</script>
<script src="<c:url value='/resources/global/js/jquery/plugins/exif.js' context='/egi'/>"></script>
<script src="<c:url value='/resources/app/js/hoarding.js'/>"></script>
