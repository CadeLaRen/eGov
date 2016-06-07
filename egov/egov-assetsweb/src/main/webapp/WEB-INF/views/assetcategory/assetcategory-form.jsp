<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">Asset Category</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<c:if test="${codeGenerationMode == 'Manual'}">
							<label class="col-sm-3 control-label text-right"><spring:message
									code="lbl.code" /> <span class="mandatory"></span> </label>
							<div class="col-sm-3 add-margin">
								<form:input path="code"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="50" required="required" />
								<form:errors path="code" cssClass="error-msg" />
							</div>
						</c:if>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.name" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="name"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="100" required="required" />
							<form:errors path="name" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.assettype" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="assetType" id="assetType"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${assetTypes}" required="required" />
							</form:select>
							<form:errors path="assetType" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.parent" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="parent" id="parent" cssClass="form-control"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${assetCategorys}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="parent" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.depreciationmethod" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="depreciationMethod" id="depreciationMethod"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${depreciationMethods}" />
							</form:select>
							<form:errors path="depreciationMethod" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.assetaccountcode" /> <span class="mandatory"></span>
						</label>
						<div class="col-sm-3 add-margin">
							<form:select path="assetAccountCode" id="assetAccountCode"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<c:forEach var="accountCodes" items="${accountCodes}">
									<option value="${accountCodes.id}">
										${accountCodes.glcode} - ${accountCodes.name}</option>
								</c:forEach>
							</form:select>
							<form:errors path="assetAccountCode" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.accdepaccountcode" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="accDepAccountCode" id="accDepAccountCode"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<c:forEach var="accountDeps" items="${accountDeps}">
									<option value="${accountDeps.id}">
										${accountDeps.glcode} - ${accountDeps.name}</option>
								</c:forEach>
							</form:select>
							<form:errors path="accDepAccountCode" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.revaccountcode" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="revAccountCode" id="revAccountCode"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<c:forEach var="accountRevRess" items="${accountRevRess}">
									<option value="${accountRevRess.id}">
										${accountRevRess.glcode} - ${accountRevRess.name}</option>
								</c:forEach>
							</form:select>
							<form:errors path="revAccountCode" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.depexpaccountcode" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="depExpAccountCode" id="depExpAccountCode"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<c:forEach var="accountDepExps" items="${accountDepExps}">
									<option value="${accountDepExps.id}">
										${accountDepExps.glcode} - ${accountDepExps.name}</option>
								</c:forEach>
							</form:select>
							<form:errors path="depExpAccountCode" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.uom" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="uom" id="uom" cssClass="form-control"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${uOMs}" itemValue="id" itemLabel="uom" />
							</form:select>
							<form:errors path="uom" cssClass="error-msg" />
						</div>
					</div>
					<script>
          $('#assetAccountCode').val(${assetCategory.assetAccountCode.id}); 
          $('#accDepAccountCode').val(${assetCategory.accDepAccountCode.id}); 
          $('#revAccountCode').val(${assetCategory.revAccountCode.id}); 
          $('#depExpAccountCode').val(${assetCategory.depExpAccountCode.id}); 
          </script>
					<input type="hidden" name="assetCategory"
						value="${assetCategory.id}" />
					 <%@ include
						file="../categorypropertytype/categorypropertytype-form.jsp"%>