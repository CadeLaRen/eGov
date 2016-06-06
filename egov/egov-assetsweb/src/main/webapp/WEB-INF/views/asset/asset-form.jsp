<div class="main-content">
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-primary" data-collapsed="0">
        <div class="panel-heading">
          <div class="panel-title">Asset</div>
        </div>
        <div class="panel-body">
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.code" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="code" class="form-control text-left patternvalidation" data-pattern="alphanumeric"
                maxlength="50" required="required" />
              <form:errors path="code" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.name" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="name" class="form-control text-left patternvalidation" data-pattern="alphanumeric"
                maxlength="256" required="required" />
              <form:errors path="name" cssClass="error-msg" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.assetcategory" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="assetCategory" id="assetCategory" onchange="loadCustomFields()" cssClass="form-control" required="required"
                cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${assetCategorys}" itemValue="id" itemLabel="name" />
              </form:select>
              <form:errors path="assetCategory" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.department" /> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="department" id="department" cssClass="form-control" cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${departments}" itemValue="id" itemLabel="name" />
              </form:select>
              <form:errors path="department" cssClass="error-msg" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.area" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="area" id="area" cssClass="form-control"  
                cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${boundarys}" itemValue="id" itemLabel="name" />
              </form:select>
              <form:errors path="area" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.location" /> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="location" id="location" cssClass="form-control" cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${boundarys}" itemValue="id" itemLabel="name" />
              </form:select>
              <form:errors path="location" cssClass="error-msg" />
            </div>
          </div>
         
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.assetdetails" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="assetDetails" class="form-control text-left patternvalidation"
                data-pattern="alphanumeric" maxlength="0" />
              <form:errors path="assetDetails" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.modeofacquisition" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="modeOfAcquisition" id="modeOfAcquisition" cssClass="form-control" required="required"
                cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${modeOfAcquisitions}"  />
              </form:select>
              <form:errors path="modeOfAcquisition" cssClass="error-msg" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.status" /> <span
              class="mandatory"></span> </label>
            <div class="col-sm-3 add-margin">
              <form:select path="status" id="status" cssClass="form-control"  
                cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${egwStatuss}" itemValue="id" itemLabel="code" />
              </form:select>
              <form:errors path="status" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.description" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="description" class="form-control text-left patternvalidation"
                data-pattern="alphanumeric" maxlength="256" />
              <form:errors path="description" cssClass="error-msg" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.dateofcreation" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="dateOfCreation" class="form-control datepicker" data-date-end-date="0d"
                data-inputmask="'mask': 'd/m/y'" />
              <form:errors path="dateOfCreation" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.remarks" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="remarks" class="form-control text-left patternvalidation" data-pattern="alphanumeric"
                maxlength="1024" />
              <form:errors path="remarks" cssClass="error-msg" />
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.accdepreciation" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="accDepreciation" class="form-control text-right patternvalidation" data-pattern="number" />
              <form:errors path="accDepreciation" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.length" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="length" class="form-control text-right patternvalidation" data-pattern="number" />
              <form:errors path="length" cssClass="error-msg" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.width" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="width" class="form-control text-right patternvalidation" data-pattern="number" />
              <form:errors path="width" cssClass="error-msg" />
            </div>
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.totalarea" /> </label>
            <div class="col-sm-3 add-margin">
              <form:input path="totalArea" class="form-control text-right patternvalidation" data-pattern="number" />
              <form:errors path="totalArea" cssClass="error-msg" />
            </div>
          </div>
          <div id="customFieldsDiv">
          </div>
            <input type="hidden" name="asset" value="${asset.id}" />