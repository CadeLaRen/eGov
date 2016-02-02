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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<div class="panel-body">
		<c:if test="${sewerageApplicationDetails.connection.legacy=='false' && mode!='addconnection' && mode!='changeOfUse'}">
			<div class="row add-border">
				<div class="col-xs-3 add-margin">
					<spring:message code="lbl.ack.number"/>
				</div>
				<div class="col-xs-3 add-margin view-content" id="applicationNumber">
					<c:choose>
						<c:when test="${not empty sewerageApplicationDetails.applicationNumber}">
							<c:out value="${sewerageApplicationDetails.applicationNumber}" />
						</c:when>
						<c:otherwise><spring:message code="lb.NA.code"/></c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-3 add-margin"><spring:message code="lbl.application.date"/></div>
				<div class="col-xs-3 add-margin view-content" id="applicationDate">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${sewerageApplicationDetails.applicationDate}" />
				</div>
			</div>
		</c:if>	
	
		<div class="row add-border">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.ptassesmentnumber"/></div>
			<div class="col-xs-3 add-margin view-content" id='propertyIdentifier'>
				<c:out value="${sewerageApplicationDetails.applicationNumber}" />
			</div>
			<div class="col-xs-3 add-margin"><spring:message code="lbl.dhsc.number"/></div>
			<div class="col-xs-3 add-margin view-content">
				<c:choose>
					<c:when test="${not empty sewerageApplicationDetails.connection.dhscNumber}">
						<c:out value="${sewerageApplicationDetails.connection.dhscNumber}" />
					</c:when>
					<c:otherwise><spring:message code="lb.NA.code"/></c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.mobileNo"/></div>
			<div class="col-xs-3 add-margin view-content" id="mobileNumber"><c:out value="${mobileNumber}"/></div>
			<div class="col-xs-3 add-margin"><spring:message code="lbl.email"/> </div>
			<div class="col-xs-3 add-margin view-content" id="email" ><c:out value="${emailAddress}"/></div>
		</div>
		<div class="row add-border">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.applicantname"/></div>
			<div class="col-xs-3 add-margin view-content" id="applicantname"><c:out value="${applicantName}"/></div>
			<div class="col-xs-3 add-margin"><spring:message code="lbl.locality" /></div>
			<div class="col-xs-3 add-margin view-content" id="locality"><c:out value="${locality}"/></div>
		</div>
		<div class="row add-border">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.address" /></div>
			<div class="col-xs-3 add-margin view-content" id="propertyaddress" >
				<c:out value="${propertyAddress}"/>
			</div>
			<div class="col-xs-3 add-margin"><spring:message code="lbl.zonewardblock"/></div>
			<div class="col-xs-3 add-margin view-content" id="zonewardblock">
				<c:out value="${zoneWardBlockDetails}"/>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.aadhar"/></div>
			<div class="col-xs-3 add-margin view-content" id="aadhaar">
				<c:out value="${aadhaarNumber}"/>
			</div>
			<div class="col-xs-3 add-margin"></div>
			<div class="col-xs-3 add-margin view-content"></div>
		</div>
		<div class="row">
			<div class="col-xs-3 add-margin"><spring:message code="lbl.pt.tax"/></div>
			<div class="col-xs-3 add-margin view-content" id="propertytaxdue">
				<c:out value="${propertyTax}"/>
			</div>
			<div class="col-xs-3 add-margin"><spring:message code="lbl.current.due"/></div>
			<c:choose>
				<c:when test="${null!= mode && sewerageTaxDue > 0}">
					<div class="col-xs-3 add-margin view-content error-msg"><c:out value="${sewerageTaxDue}" /></div>
				</c:when>	
				<c:otherwise>
					<div class="col-xs-3 add-margin view-content"><spring:message code="lb.ZERO.code"/></div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

