<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">Asset</div></div><div class="panel-body custom"><div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.code" />
</div><div class="col-sm-3 add-margin view-content">
${asset.code}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.name" />
</div><div class="col-sm-3 add-margin view-content">
${asset.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.assetcategory" />
</div><div class="col-sm-3 add-margin view-content">
${asset.assetCategory.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.department" />
</div><div class="col-sm-3 add-margin view-content">
${asset.department.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.area" />
</div><div class="col-sm-3 add-margin view-content">
${asset.area.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.location" />
</div><div class="col-sm-3 add-margin view-content">
${asset.location.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.street" />
</div><div class="col-sm-3 add-margin view-content">
${asset.street.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.ward" />
</div><div class="col-sm-3 add-margin view-content">
${asset.ward.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.assetdetails" />
</div><div class="col-sm-3 add-margin view-content">
${asset.assetDetails}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.modeofacquisition" />
</div><div class="col-sm-3 add-margin view-content">
${asset.modeOfAcquisition}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.status" />
</div><div class="col-sm-3 add-margin view-content">
${asset.status.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.description" />
</div><div class="col-sm-3 add-margin view-content">
${asset.description}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.dateofcreation" />
</div><div class="col-sm-3 add-margin view-content">
<fmt:formatDate pattern="MM/dd/yyyyy" value="${asset.dateOfCreation} />
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.remarks" />
</div><div class="col-sm-3 add-margin view-content">
${asset.remarks}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.preparedby" />
</div><div class="col-sm-3 add-margin view-content">
${asset.preparedBy.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.grossvalue" />
</div><div class="col-sm-3 add-margin view-content">
${asset.grossValue}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.accdepreciation" />
</div><div class="col-sm-3 add-margin view-content">
${asset.accDepreciation}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.length" />
</div><div class="col-sm-3 add-margin view-content">
${asset.length}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.width" />
</div><div class="col-sm-3 add-margin view-content">
${asset.width}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.totalarea" />
</div><div class="col-sm-3 add-margin view-content">
${asset.totalArea}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.sourcepath" />
</div><div class="col-sm-3 add-margin view-content">
${asset.sourcePath}
</div></div></div></div></div><div class="row text-center"><div class="add-margin"><a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">Close</a></div></div>