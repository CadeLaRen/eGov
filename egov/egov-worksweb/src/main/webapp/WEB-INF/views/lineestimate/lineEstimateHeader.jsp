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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="panel panel-primary" data-collapsed="0">
	<div class="panel-heading">
		<div class="panel-title" style="text-align:center;"><spring:message code="lineestimate.header" /></div>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lineestimate.subject" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:hidden path="id" name="id" value="${id}" class="form-control table-input hidden-input"/>
				<form:textarea name="subject" path="subject" id="subject" class="form-control" value = "${subject}" maxlength="256" required="required"></form:textarea>
				<form:errors path="subject" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lineestimate.fund" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="fund" data-first-option="false" class="form-control" id="fund" required="required">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
					<form:options items="${funds}" itemValue="id" itemLabel="name"/>
				</form:select>
				<form:errors path="fund" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lineestimate.reference" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:textarea name="reference" path="reference" id="lineestimate.reference" value="${reference}" class="form-control" maxlength="1024" required="required"></form:textarea>
				<form:errors path="reference" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lineestimate.function" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="function" data-first-option="false" name="function" class="form-control" id="function" required="required">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
					<form:options items="${functions}" itemValue="id" itemLabel="name"/>
				</form:select>
				<form:errors path="function" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lineestimate.description" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:textarea name="description" path="description" id="description" class="form-control" value = "${description}" maxlength="1024" required="required"></form:textarea>
				<form:errors path="description" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lineestimate.budgethead" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="budgetHead" data-first-option="false" id="budgetHead" class="form-control" required="required">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
					<form:options items="${budgetHeads}" itemValue="id" itemLabel="name"/>
				</form:select>
				<form:errors path="budgetHead" cssClass="add-margin error-msg" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lineestimate.scheme" /></label>
			<div class="col-sm-3 add-margin">
				<form:select path="scheme" data-first-option="false" id="scheme" class="form-control" onchange="getSubSchemsBySchemeId(this.value)">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
					<c:if test="${!schemes.isEmpty()}">
						<form:options items="${schemes}" itemValue="id" itemLabel="name"/>
					</c:if>
				</form:select>
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lineestimate.subscheme" /></label>
			<div class="col-sm-3 add-margin">
				<form:select path="subScheme" data-first-option="false" id="subScheme" class="form-control">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label text-right"><spring:message code="lineestimate.date" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:input path="lineEstimateDate" name="lineEstimateDate" type="text" class="form-control" value="${lineEstimateDate}" maxlength="12" readonly="true"/>
				<form:errors path="lineEstimateDate" cssClass="add-margin error-msg" />
			</div>
			<label class="col-sm-2 control-label text-right"><spring:message code="lineestimate.executingdepartment" /><span class="mandatory"></span></label>
			<div class="col-sm-3 add-margin">
				<form:select path="executingDepartment" data-first-option="false" id="executingDepartments" class="form-control" required="required">
					<form:option value=""><spring:message code="lbl.select" /></form:option>
					<form:options items="${executingDepartments}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="executingDepartment" cssClass="add-margin error-msg" />
			</div>
		</div>
	</div>
</div>
