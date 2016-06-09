<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2016>  eGovernments Foundation
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	
		
				<table class="table table-striped table-bordered" id="inspectionChargesDetails" style="width:50%;margin:0 auto;">
					<thead>
					      <tr>
					        <th class="text-center"><spring:message code="lbl.slno" /></th>
							<th style="width:50%;"><spring:message code="lbl.feestype" /></th>
							<th class="text-right"><spring:message code="lbl.amount" /></th>
					      </tr>
				    </thead>
				<tbody>
		<c:if test="${mode == 'edit'}"> 
			<c:forEach var="inspection" items="${sewerageApplicationDetails.connectionFees}" varStatus="counter">
				<c:if test="${sewerageApplicationDetails.connectionFees[counter.index].feesDetail.code == 'INSPECTIONCHARGE' }">
				<tr>
					<td  class="text-center"><c:out value="${counter.index+1}" /></td>
					<td id="description"><c:out value="${inspection.feesDetail.description}" /></td>
					<td class="text-right"><c:out value="${inspection.amount}" /></td>
				</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:choose>
			<c:when test="${estimationChargesExists =='no' || estimationChargesExists == null}">
					<c:forEach items="${inspectionFeeMasterList}" var="fm" varStatus="counter"> 
				       	<tr class=""> 
				       			<c:choose>
				       				<c:when test="${mode == 'edit'}">
										<td class="text-center"><span id="slNo1">${sewerageApplicationDetails.connectionFees.size()+counter.index+1}</span></td>
									</c:when>
									<c:otherwise>
										<td class="text-center"><span id="slNo1">${counter.index+1}</span></td>
									</c:otherwise>
								</c:choose>	
									<td >
									<c:out value="${fm.description}"/><c:if test="${fm.isMandatory == 'true'}"><span class="mandatory"></span>  </c:if> </td>   
									<td class="text-right">  
											<form:hidden path="connectionFees[${counter.index}].feesDetail" value="${fm.id}"></form:hidden>      
									<c:choose>			
									  <c:when test="${fm.isFixedRate == 'true'}">  	
											<form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" readonly="true" /> 
								      </c:when>  
								  <c:otherwise>  
								    <c:choose>
								     	<c:when test="${fm.isMandatory == 'true'}">
								     	     <form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" required="required" /> 
								     	 </c:when>
								     	<c:otherwise>   
								     	    <form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" /> 
								        </c:otherwise> 	
								    
								     </c:choose>	
								      </c:otherwise> 	 
								      </c:choose>	
									</td> 
							    </tr>  
						
					</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach items="${sewerageApplicationDetails.connectionFees}" var="fm" varStatus="counter">  
				<c:if test="${sewerageApplicationDetails.connectionFees[counter.index].feesDetail.code != 'INSPECTIONCHARGE' }">
				       	<tr class=""> 
										<td class="text-center"><span id="slNo1">${counter.index+1}</span></td>
									<td >
									<c:out value="${fm.feesDetail.description}"/><c:if test="${fm.feesDetail.isMandatory == 'true'}"><span class="mandatory"></span>  </c:if> </td>   
									<td class="text-right">  
											<%-- <form:hidden path="connectionFees[${counter.index}].feesDetail" value="${fm.feesDetail.id}"></form:hidden> --%>      
									<c:choose>			
									  <c:when test="${fm.feesDetail.isFixedRate == 'true'}">  	
											<form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" readonly="true" /> 
								      </c:when>  
								  <c:otherwise>  
								    <c:choose>
								     	<c:when test="${fm.feesDetail.isMandatory == 'true'}">
								     	     <form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" required="required" /> 
								     	 </c:when>
								     	<c:otherwise>   
								     	    <form:input type="text" class="form-control table-input text-right patternvalidation" data-pattern="decimalvalue" path="connectionFees[${counter.index}].amount" id="feesDetail${counter.index}amount"  value="${fm.amount}" /> 
								        </c:otherwise> 	
								    
								     </c:choose>	
								      </c:otherwise> 	 
								      </c:choose>	
									</td> 
							    </tr>  
						</c:if>	
					</c:forEach>
		</c:otherwise>
		</c:choose>
				</tbody>
				</table>
		

