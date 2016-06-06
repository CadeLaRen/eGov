<div class="row">
  <div class="col-sm-12">
    <br>
    <table class="table table-bordered" id="result">
      <thead>
        <th><spring:message code="lbl.name" /></th>
        <th><spring:message code="lbl.datatype" /></th>
        <th><spring:message code="lbl.format" /></th>
        <th><spring:message code="lbl.isactive" /></th>
        <th><spring:message code="lbl.ismandatory" /></th>
        <th><spring:message code="lbl.enumvalues" /></th>
        <th><spring:message code="lbl.action" /></th>
      </thead>
      <c:choose>
      <c:when test="${not empty assetCategory.getCategoryProperties()}">
        <tbody>
          <c:forEach items="${assetCategory.categoryProperties}" var="categoryProperties" varStatus="vs">
            <tr id="resultrow${vs.index}">
              <td><form:hidden path="categoryProperties[${vs.index}].id"/>
              <form:input path="categoryProperties[${vs.index}].name"
                  class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="50" />
                <form:errors path="categoryProperties[${vs.index}].name" cssClass="error-msg" /></td>
              <td><form:select path="categoryProperties[${vs.index}].dataType" id="dataType"
                  cssClass="form-control" cssErrorClass="form-control error">
                  <form:option value="">
                    <spring:message code="lbl.select" />
                  </form:option>
                  <form:options items="${categoryPropertyDataTypes}" />
                </form:select> <form:errors path="categoryProperties[${vs.index}].dataType" cssClass="error-msg" /></td>
              <td><form:input path="categoryProperties[${vs.index}].format"
                  class="form-control text-left patternvalidation" data-pattern="alphanumeric"
                  maxlength="200" /> <form:errors path="categoryProperties[${vs.index}].format"
                  cssClass="error-msg" /></td>
              <td><form:checkbox path="categoryProperties[${vs.index}].isActive" /> <form:errors
                  path="categoryProperties[${vs.index}].isActive" cssClass="error-msg" /></td>
              <td><form:checkbox path="categoryProperties[${vs.index}].isMandatory" /> <form:errors
                  path="categoryProperties[${vs.index}].isMandatory" cssClass="error-msg" /></td>
              <td><form:input path="categoryProperties[${vs.index}].enumValues"
                  class="form-control text-left patternvalidation" data-pattern="alphanumeric"
                  maxlength="300" /> <form:errors path="categoryProperties[${vs.index}].enumValues"
                  cssClass="error-msg" /></td>
              <td><span class="add-padding">
                  <button type="button" id="del-row" class="btn btn-primary" onclick="deleteThisRow(this)">Delete
                    Row</button> </i>
              </span></td>
            </tr>
          </c:forEach>
        </tbody>
      </c:when>
      <c:otherwise>
        <tbody>
          <tr id="resultrow0">
            <td><form:input path="categoryProperties[0].name" class="form-control text-left patternvalidation"
                data-pattern="alphanumeric" maxlength="50" /> <form:errors path="categoryProperties[0].name"
                cssClass="error-msg" /></td>
            <td><form:select path="categoryProperties[0].dataType" id="dataType" cssClass="form-control"
                cssErrorClass="form-control error">
                <form:option value="">
                  <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${categoryPropertyDataTypes}" />
              </form:select> <form:errors path="categoryProperties[0].dataType" cssClass="error-msg" /></td>
            <td><form:input path="categoryProperties[0].format" class="form-control text-left patternvalidation"
                data-pattern="alphanumeric" maxlength="200" /> <form:errors path="categoryProperties[0].format"
                cssClass="error-msg" /></td>
            <td><form:checkbox path="categoryProperties[0].isActive" /> <form:errors
                path="categoryProperties[0].isActive" cssClass="error-msg" /></td>
            <td><form:checkbox path="categoryProperties[0].isMandatory" /> <form:errors
                path="categoryProperties[0].isMandatory" cssClass="error-msg" /></td>
            <td><form:input path="categoryProperties[0].enumValues"
                class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="300" /> <form:errors
                path="categoryProperties[0].enumValues" cssClass="error-msg" /></td>
            <td><span class="add-padding">
                <button type="button" id="del-row" class="btn btn-primary" onclick="deleteThisRow(this)">Delete
                  Row</button> </i>
            </span></td>
          </tr>
        </tbody>
        </c:otherwise>   
        </c:choose>
    </table>
  </div>
  <div class="col-sm-12 text-center">
    <button type="button" id="addrow" class="btn btn-primary" >Add Row</button>
  </div>
</div>

