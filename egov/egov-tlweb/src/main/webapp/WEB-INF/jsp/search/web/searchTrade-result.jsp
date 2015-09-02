<%@ include file="/includes/taglibs.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
	function callEdit(obj, id){
		path='<%=path%>';	
		licenseId = id;
		var actionValue = obj.options[obj.selectedIndex].value;
		if(actionValue!=-1&& actionValue!=-2&& actionValue!=-3){
			window.open(path+actionValue+Number(id),"winPop","scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no,alwaysLowered=yes");
		} else if(actionValue==-2){
			var appType = 'RENEWAL';
			makeJSONCall(["licenseAppType"],'ajaxTradeLicense!checkLicenseAppType.action',{licenseAppType:appType},licenseSuccessHandler,licenseFailureHandler) ;
		}	
	}
</script>

<s:if test="pagedResults != null && pagedResults.getList() != null && !pagedResults.getList().isEmpty()">
	<br />
	<fieldset>
		<legend align="center">
			<b>Search Result</b>
		</legend>
		
		<s:if test="(noticeNumber==null && docNumber==null && noticeType=='-1' && noticeFromDate==null && noticeToDate==null) || (noticeNumber=='' && docNumber=='' && noticeType=='-1' && noticeFromDate==null && noticeToDate==null)">
		<display:table name="pagedResults" uid="license" style="background-color:#e8edf1;width:98%;padding:0px;margin:10 0 0 5px;" pagesize="20" export="true" requestURI="searchTrade!search.action?reportSize=${reportSize}" excludedParams="reportSize" cellpadding="0" cellspacing="0">
			<display:column class="blueborderfortd" title="S.No" style="border-left:1px solid #E9E9E9">
				<s:property value="%{#attr.license_rowNum+(page == 0  ? 0: (page-1))*10}" />
			</display:column>
			<display:column class="blueborderfortd" title="Trade License Number" media="html">
				<c:if test="${license.licenseNumber == null || license.licenseNumber == ''}">
					&nbsp;
				</c:if>
				<c:choose>
					<c:when test='${license.licenseNumber != null && license.licenseNumber != ""}'>
						<a href="../../viewtradelicense/web/viewTradeLicense!view.action?id=${license.id}" target="_blank"> ${license.licenseNumber} </a>
					</c:when>
					<c:when test='${license.tempLicenseNumber != null && license.tempLicenseNumber != ""}'>
						<a href="../../viewtradelicense/web/viewTradeLicense!view.action?id=${license.id}" target="_blank"> ${license.tempLicenseNumber} </a>
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Trade License Number" media="excel pdf">
				<c:choose>
					<c:when test='${license.licenseNumber != null && license.licenseNumber != ""}'>
					 ${license.licenseNumber}
					</c:when>
					<c:when test='${license.tempLicenseNumber != null && license.tempLicenseNumber != ""}'>
					${license.tempLicenseNumber}
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Application Number" media="html">
				<c:if test="${license.applicationNumber == null || license.applicationNumber == ''}">
					&nbsp;
				</c:if>
				<c:choose>
					<c:when test='${license.applicationNumber != null && license.applicationNumber != ""}'>
						<a href="../../viewtradelicense/web/viewTradeLicense!view.action?id=${license.id}" target="_blank"> ${license.applicationNumber} </a>
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Application Number" media="excel pdf">
				<c:choose>
					<c:when test='${license.applicationNumber != null && license.applicationNumber != ""}'>
					 ${license.applicationNumber}
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Application Date">
				<fmt:formatDate value="${license.applicationDate}" pattern="dd/MM/yyyy" />
			</display:column>
			<display:column class="blueborderfortd" title="Applicant Name">
				<c:if test="${license.licensee.applicantName == null || license.licensee.applicantName ==''}">
					&nbsp;
				</c:if>
				<c:out value="${license.licensee.applicantName}" />
			</display:column>
			<display:column class="blueborderfortd" title="Establishment Name">
				<c:choose>
					<c:when test="${license.nameOfEstablishment != null || license.nameOfEstablishment !=''}">
						<c:out value="${license.nameOfEstablishment}" />
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Address">
				<c:out value="${license.address}" />
			</display:column>
			<display:column class="blueborderfortd" title="Zone">
				<s:if test="%{#attr.license.boundary.boundaryType.name!='Ward'}">
					<c:out value="${license.boundary.name}" />
				</s:if>
				<s:elseif test="%{#attr.license.boundary.parent.name == null || #attr.license.boundary.parent.name ==''}">
					&nbsp;
				</s:elseif>
				<s:else>
					<c:out value="${license.boundary.parent.name}" />
			   </s:else>
			</display:column>
			<display:column class="blueborderfortd" title="Ward">
				<c:if test="${license.boundary.name == null || license.boundary.name ==''}">
					&nbsp;
				</c:if>
				<c:if test="${license.boundary.boundaryType.name =='Ward'  }">
				<c:out value="${license.boundary.name}" />
				</c:if>
			</display:column>
			<display:column class="blueborderfortd" title="Trade Name">
				<c:if test="${license.tradeName.name == null || license.tradeName.name ==''}">
					&nbsp;
				</c:if>
				<c:out value="${license.tradeName.name}" />
			</display:column>
			<display:column class="blueborderfortd" title="License Issued Date">
				<fmt:formatDate value="${license.dateOfCreation}" pattern="dd/MM/yyyy" />
			</display:column>
			<display:column class="blueborderfortd" title="Action" style="width:10%" media="html">
				<select onchange="callEdit(this,'${license.id}')">
					<option value="-1">
						<s:text name="license.default.select" />
					</option>
					<s:if test="%{#attr.license.status.statusCode!='CAN'}">
						<s:if test="%{#attr.license.status.statusCode!='UWF'}">
						<s:if test="%{roleName.contains('TLAPPROVER')}">
							<option value="/cancellation/web/cancelLicense!newForm.action?licenseId=">
								<s:text name="Cancel Trade" />
							</option>
							</s:if>
							<s:if test="%{roleName.contains('TLCREATOR')}">
							<s:if test="%{#attr.license.disablePrintCertificate() != true}">
							<option value="/viewtradelicense/web/viewTradeLicense!duplicateCertificate.action?model.id=">
								<s:text name="Print License" />
							</option>
							</s:if>
							</s:if>
							<s:if test="%{#attr.license.status.statusCode=='ACT'}">
							<s:if test="%{roleName.contains('TLCREATOR')}">
								<option value="/transfer/web/transferTradeLicense!newForm.action?model.id=">
									<s:text name="license.action.transfer" />
								</option>
								<s:if test="%{#attr.license.oldLicenseNumber != null && #attr.license.oldLicenseNumber != ''}">
									<option value="/newtradelicense/web/editTradeLicense!beforeEdit.action?model.id=">
										<s:text name="license.action.modify" />
									</option>
								</s:if>
								</s:if>
							</s:if>
							<s:if test="%{roleName.contains('OPERATOR')}">
							<s:if test="%{#attr.license.isPaid() != true && #attr.license.status.statusCode=='ACK' && #attr.license.isWorkFlowStateRejected() != true}">
								<option value="/web/integration/licenseBillCollect.action?licenseId=">
									<s:text name="Collect Fee" />
								</option>
							</s:if>
							</s:if>
						</s:if>
						<s:if test="%{roleName.contains('OPERATOR')}">
						<s:if test="%{#attr.license.isPaid() != true && #attr.license.status.statusCode=='UWF' && #attr.license.isWorkFlowStateRejected() != true}">
							<option value="/web/integration/licenseBillCollect.action?licenseId=">
								<s:text name="Collect Fee" />
							</option>
						</s:if>
						</s:if>
						<s:if test="%{roleName.contains('TLCREATOR')}">
						<s:if test="%{#attr.license.licenseNumber != null && #attr.license.licenseNumber != ''}">
						<option value="/web/objection/objection!newForm.action?licenseId=">
							<s:text name="Record Objection" />
						</option>
						</s:if>
						</s:if>
						<s:if test="%{roleName.contains('TLCREATOR')}">
						<s:if test="%{#attr.license.status.statusCode=='SUS'}">
							<option value="/revokesuspension/web/revokeSuspension!newForm.action?licenseId=">
								<s:text name="Revoke Suspension" />
							</option>
						</s:if>
						</s:if>
					</s:if>
          
					<s:set name="dateOfExpiry" value="%{#attr.license.dateOfExpiry}" />
					<s:if test="%{roleName.contains('TLAPPROVER') || roleName.contains('TLVALIDATOR')}">
					<s:if test="%{checkForRenewalNotice(#dateOfExpiry)}">
						<option value="/renew/web/tradeRenewalNotice!renewalNotice.action?model.id=">
							<s:text name="Renewal Notice" />
						</option>
					</s:if>
					</s:if>
					<s:if test="%{roleName.contains('TLCREATOR') || roleName.contains('TLAPPROVER')}">
					<s:if test="%{isRenewable(#attr.license.id)}">
				    <s:if test="%{#attr.license.status.statusCode=='ACT'}">
                    <s:set name="licenseId" value="%{#attr.license.dateOfExpiry}" />
						<option value="/newtradelicense/web/newTradeLicense!beforeRenew.action?model.id=">
							<s:text name="license.action.renew" />
						</option>
					</s:if>
                    </s:if>
                    </s:if>
                    <s:if test="%{roleName.contains('TLCREATOR')}">
                    <s:if test="%{isNocApplicable(#attr.license.id)}">
	                    <option value="/viewtradelicense/web/viewTradeLicense!duplicateNoc.action?model.id=">
							<s:text name="Reprint NOC" />
						</option>
                    </s:if>
                    </s:if>
				</select>
			</display:column>
			<display:setProperty name="basic.show.header" value="true" />
			<display:setProperty name="basic.empty.showtable" value="true" />
			<display:setProperty name="export.excel.class" value="org.egov.infstr.displaytag.export.EGovExcelView" />
			<display:setProperty name="export.pdf.class" value="org.egov.infstr.displaytag.export.EGovPdfView" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.excel" value="true" />
			<display:setProperty name="export.excel.filename" value="tradeLicense-searchTrade.xls" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.pdf.filename" value="tradeLicense-searchTrade.pdf" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="paging.banner.placement" value="top" />
		</display:table>
		</s:if>
		<s:else>
		<display:table name="pagedResults" uid="license" style="background-color:#e8edf1;width:98%;padding:0px;margin:10 0 0 5px;" pagesize="20" requestURI="searchTrade!search.action?reportSize=${reportSize}" excludedParams="reportSize" cellpadding="0" cellspacing="0">
			<display:column class="blueborderfortd" title="S.No" style="border-left:1px solid #E9E9E9">
				<s:property value="%{#attr.license_rowNum+(page == 0  ? 0: (page-1))*10}" />
			</display:column>
			<display:column class="blueborderfortd" title="Notice Number">
				<c:if test="${license.noticeNumber == null || license.noticeNumber == ''}">
					&nbsp;
				</c:if>
				<c:choose>
					<c:when test='${license.noticeNumber != null && license.noticeNumber != ""}'>
						<center><a href="/egi/docmgmt/ajaxFileDownload!downloadNotice.action?fileName=${license.docNumber}.htm&docNumber=${license.docNumber}&moduleName=${license.moduleName}" target="_blank"> ${license.noticeNumber} </a></center>
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Notice Type">
				<c:choose>
					<c:when test='${license.noticeType == null || license.noticeType == ""}'>
					 &nbsp;
					</c:when>
					<c:when test='${license.noticeType != null && license.noticeType != ""}'>
					<center>${license.noticeType}</center>
					</c:when>
				</c:choose>
			</display:column>
			<display:column class="blueborderfortd" title="Document Number">
				<c:choose>
					<c:when test='${license.docNumber == null || license.docNumber == ""}'>
					 &nbsp;
					</c:when>
					<c:when test='${license.docNumber != null && license.docNumber != ""}'>
					<center>${license.docNumber}</center>
					</c:when>
				</c:choose>
			</display:column>
			
			<display:column class="blueborderfortd" title="Notice Date">
				<center><fmt:formatDate value="${license.noticeDate}" pattern="dd/MM/yyyy" /></center>
			</display:column>
			
			<display:column class="blueborderfortd" title="Module Name" >
				<c:choose>
					<c:when test='${license.moduleName == null || license.moduleName == ""}'>
					 &nbsp;
					</c:when>
					<c:when test='${license.moduleName != null && license.moduleName != ""}'>
					<center>${license.moduleName}</center>
					</c:when>
				</c:choose>
			</display:column>
			
			<display:column class="blueborderfortd" title="Objection Number" >
				<c:choose>
					<c:when test='${license.objection.number == null || license.objection.number == ""}'>
					 &nbsp;
					</c:when>
					<c:when test='${license.objection.number != null && license.objection.number != ""}'>
					<center>${license.objection.number}</center>
					</c:when>
				</c:choose>
			</display:column>
		</display:table>
		</s:else>
	</fieldset>
	
</s:if>

<% if(request.getAttribute("hasResult") != null && (!(Boolean)request.getAttribute("hasResult"))){%>
<fieldset>
	<legend align="center">
		<b>Search Result</b>
	</legend>
	<div class="subheadnew">
	<s:if test="(noticeNumber==null && docNumber==null && noticeType=='-1' && noticeFromDate==null && noticeToDate==null) || (noticeNumber=='' && docNumber=='' && noticeType=='-1' && noticeFromDate==null && noticeToDate==null)">
		<s:text name="search.result.notrades" />
		</s:if>
		<s:else>
		<s:text name="search.result.nonotices" />
		</s:else>
	</div>
</fieldset>
<%} %>
