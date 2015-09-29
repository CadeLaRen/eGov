<!--
	eGov suite of products aim to improve the internal efficiency,transparency, 
    accountability and the service delivery of the government  organizations.
 
    Copyright (C) <2015>  eGovernments Foundation
 
	The updated version of eGov suite of products as by eGovernments Foundation 
    is available at http://www.egovernments.org
 
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
 
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or 
    http://www.gnu.org/licenses/gpl.html .
 
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
 
 	1) All versions of this program, verbatim or modified must carry this 
 	   Legal Notice.
 
 	2) Any misrepresentation of the origin of the material is prohibited. It 
 	   is required that all modified versions of this material be marked in 
 	   reasonable ways as different from the original version.
 
 	3) This license does not grant any rights to any user of the program 
 	   with regards to rights under trademark law for use of the trade names 
 	   or trademarks of eGovernments Foundation.
 
   	In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
-->

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><s:if test="mode=='create' || mode=='edit'">
		<s:text name='dataentry.title' />
	</s:if></title>
<sx:head />
<!-- <script type="text/javascript" src="/ptis/resources/javascript/unitRentAgreement.js"></script> -->
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap.js' context='/egi'/>"
	type="text/javascript"></script>
<link
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>"
	rel="stylesheet" type="text/css" />
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egi'/>"
	type="text/javascript"></script>

</head>

<body onload="loadOnStartUp();">
	<s:if test="%{hasErrors()}">
		<div class="errorstyle" id="property_error_area">
			<div class="errortext">
				<s:actionerror />
			</div>
		</div>
	</s:if>

	<s:form name="CreatePropertyForm" action="createProperty-create"
		theme="simple" enctype="multipart/form-data">

		<s:push value="model">
			<s:token />

			<!-- The mode value is used in floorform.jsp file to stop from remmoving the rent agreement header icon -->
			<s:hidden name="mode" id="mode" value="%{mode}" />
			<s:hidden name="modelId" id="modelId" value="%{modelId}" />
			<div class="formmainbox">
				<div class="headingbg">
					<s:text name="dataentrypropertyheader" />
						</div>
				<table>
					
				</table>

				<%@  include file="createPropertyForm.jsp"%>
			</div>
			<div class="buttonbottom" align="center">
				<table>
					<tr>
						<td><input  type="submit" id="Create" class="btn btn-primary" value="Create" onclick="onSubmit();"/> 
							<input type="button" name="button2" id="button2" value="Close" class="btn btn-primary" onclick="window.close();" /></td>
					</tr>
				</table>
			</div>
			
		</s:push>
		
	</s:form>
	<script type="text/javascript">

jQuery.noConflict();

jQuery("#loadingMask").remove();
jQuery(function ($) {
	try { 
		$(".datepicker").datepicker({
			format: "dd/mm/yyyy"
		}); 
		}catch(e){
		console.warn("No Date Picker "+ e);
	}

		$('.datepicker').on('changeDate', function(ev){
		    $(this).datepicker('hide');
		});
		
    	
});
function loadOnStartUp() {
	enableCorresAddr();
	enableAppartnaumtLandDetails();
	makeMandatory();
	document.getElementById("appurtenantRow").style.display = "none";
	enableOrDisableSiteOwnerDetails(jQuery('input[name="propertyDetail.structure"]'));
	enableOrDisableBPADetails(jQuery('input[name="propertyDetail.buildingPlanDetailsChecked"]'));
	var appartunentLand = jQuery('input[name="propertyDetail.appurtenantLandChecked"]');
	if (jQuery(appartunentLand).is(":checked")) {
		enableAppartnaumtLandDetails();
	}
	var category = '<s:property value="%{propertyDetail.categoryType}"/>';
	document.forms[0].propTypeCategoryId.options[document.forms[0].propTypeCategoryId.selectedIndex].value = category;
	/* document.getElementById("plotArea").style.display = ""; */
	/* document.getElementById("ownerShipRow").style.display = "none";
	document.getElementById("vacantAreaRow").style.display = "none"; */
	/* document.getElementById("undivArea").style.display = "none";		
	document.getElementById("rentBox").className="hiddentext";
	document.getElementById("bldngCostId").className="hiddentext";
	document.getElementById("parentIndex").className="hiddentext";
	document.getElementById("opAlvId").className="hiddentext";
	document.getElementById("occId").className="hiddentext";
	document.getElementById("rentBox").readOnly=true;
	document.getElementById("bldngCostId").readOnly=true;
	document.getElementById("amenitiesId").disabled=true;
	document.getElementById("opAlvId").readOnly=true;
	document.getElementById("occId").readOnly=true;
	document.getElementById("parentIndex").readOnly=true;
	document.getElementById("dateOfCompletion").readOnly=true;
	document.getElementById("dateOfCompletion").className="hiddentext";
	document.getElementById("floorDetailsConfirm").style.display = "none";
	document.getElementById("waterRate").style.display = "none"; */
	
	//enableFieldsForPropType();
	//hideAddRmvBtnForResidFlats();
	//enableCorresAddr();
	//enableTaxExemptReason();
	//enableRentBox();
			
	/* var complDateStr = document.getElementById("dateOfCompletion").value;
	if(complDateStr == "" || complDateStr == "DD/MM/YYYY" || complDateStr == undefined)
	{		
		waterMarkInitialize('dateOfCompletion','DD/MM/YYYY');
	}
	var tbl = document.getElementById('floorDetails');	
	if(tbl!=null) {
		resetDetailsForTenantOnload();
	} */
	
	//populateLocationFactors();	
	//populateFloorConstTypeDropDowns();
	//toggleForResNonRes();	
	 toggleFloorDetails();
	//toggleUnitTypeAndCategory();
	//prepareUnitTypeCategories();
	//prepareUsagesForUnitTypes();
	
	/* var intervalId = -1;
	var propTypeMstr = document.getElementById("propTypeMaster");
	
	if (propTypeMstr.options[propTypeMstr.selectedIndex].text == 'Mixed') {
		intervalId = setInterval(doOnValidationErrors, 1000);
	} 
	 
	if (areUnitTypeCatsAndUsagePopulated) {
		clearInterval(intervalId);
	} 	
	
	document.getElementById("taxExemptRow").style.display = "none";		 */
	//enableSubmitButton();  
     var aadhartextboxes = jQuery('.txtaadhar');
     console.log(aadhartextboxes);
     aadhartextboxes.each(function() {
	   	if(jQuery(this).val())
	   	{
	   		  getAadharDetails(this);
	   	}
	 });
}

function onSubmit() { 

	jQuery('#gender, #guardianRelation').removeAttr('disabled');
    
	document.forms[0].action = 'createProperty-createDataEntry.action';
	<s:if test="mode=='edit'">
	document.forms[0].action = 'createProperty-updateDataEntry.action';
	</s:if>
	document.forms[0].submit;
	
   return true;
}

jQuery(window).unload(function(){
	parent.window.opener.inboxloadmethod();
});

</script>
</body>
</html>
