<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">AssetCategory</div></div><div class="panel-body custom"><div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.code" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.code}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.name" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.assettype" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.assetType.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.parent" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.parent.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.depreciationmethod" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.depreciationMethod.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.assetaccountcode" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.assetAccountCode.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.accdepaccountcode" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.accDepAccountCode.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.revaccountcode" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.revAccountCode.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.depexpaccountcode" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.depExpAccountCode.name}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.uom" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.uom.name}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.depreciationmetadatalist" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.depreciationMetaDataList}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.assets" />
</div><div class="col-sm-3 add-margin view-content">
${assetCategory.assets}
</div></div>
</div></div></div></div><div class="row text-center"><div class="add-margin"><a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">Close</a></div></div>