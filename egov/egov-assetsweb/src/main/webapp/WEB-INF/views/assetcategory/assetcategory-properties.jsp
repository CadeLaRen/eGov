<%@ include file="/includes/taglibs.jsp"%>
<c:choose>
  <c:when test="${not empty assetCategory.getCategoryProperties()}">
    <c:forEach items="${assetCategory.categoryProperties}" var="categoryProperties" varStatus="vs">
      <c:if test="${categoryProperties.isMandatory==true}">
        <c:set var="mandatoryLable" value='<span class="mandatory"></span>' />
        <c:set var="required" value='required="required" ' />
      </c:if>
      <c:if test="${vs.index%2==0}">
        <div class="form-group">
      </c:if>
      <label class="col-sm-3 control-label text-right"> ${categoryProperties.name} ${mandatoryLable} </label>
      <div class="col-sm-3 add-margin">
        <input type="hidden" name="categoryProperties[${vs.index}].id" value="${categoryProperties.id}" >
        <c:if test="${categoryProperties.dataType=='String'}">
          <input type="text" name="categoryProperties[${vs.index}].value" id="${categoryProperties.name}"
            class="form-control text-left" ${required} />
        </c:if>
        <c:if test="${categoryProperties.dataType=='Number'}">
          <input type="text" name="categoryProperties[${vs.index}].value" id="${categoryProperties.name}"
            class="form-control text-right patternvalidation" data-pattern="number" ${required} />
        </c:if>
        <c:if test="${categoryProperties.dataType=='Enumeration'}">
          <select name="categoryProperties[${vs.index}].value" id="${categoryProperties.name}"
            class="form-control" data-pattern="number" ${required}>
            <option value="">
              <spring:message code="lbl.select" />
            </option>
            <c:forTokens items="${categoryProperties.enumValues}" delims="," var="val">
            <option value="${val}">${val}</option>  
            </c:forTokens>
          </select>
        </c:if>
         <c:if test="${categoryProperties.dataType=='Date'}">
          <input type="text"  name="categoryProperties[${vs.index}].${categoryProperties.name}" id="${categoryProperties.name}"
            class="form-control datepicker" data-date-end-date="0d"
                data-inputmask="'mask': 'd/m/y'" ${required}/>
           
        </c:if>
      </div>
      <c:if test="${vs.index%2==1}">
        </div>
      </c:if>
    </c:forEach>
  </c:when>
</c:choose>
