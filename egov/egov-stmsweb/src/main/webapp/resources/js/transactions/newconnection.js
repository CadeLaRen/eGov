/*#-------------------------------------------------------------------------------
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
#-------------------------------------------------------------------------------*/
$(document).ready(function(){
	
	loadPropertyDetails();

	$('#propertyIdentifier').blur(function(){
		validateSewerageConnection();
	});
	
	$('#noOfClosetsResidential').blur(function(){
		var propertyType = $('#propertyType').val()
		var noOfClosetsResidential = $('#noOfClosetsResidential').val()
		validateClosets(propertyType , noOfClosetsResidential,true);
	});
	
	$('#noOfClosetsNonResidential').blur(function(){
		var propertyType = $('#propertyType').val()
		var noOfClosetsNonResidential = $('#noOfClosetsNonResidential').val()
		validateClosets(propertyType , noOfClosetsNonResidential,false);
	});
	

	function validateClosets(propertyType,noOfClosets,flag)
	{
			$.ajax({
				url: "/stms/ajaxconnection/check-closets-exists",      
				type: "GET",
				data: {
					propertyType : propertyType , 
					noOfClosets : noOfClosets,
					flag : flag
				},
				dataType: "json",
				success: function (response) { 
					if(response != '') {
						bootbox.alert(response);
						resetClosetDetails();
					}
				}, 
				error: function (response) {
					
					bootbox.alert("connection validation failed");
				}
			});
	}
	
	function resetClosetDetails() {
		$('#noOfClosetsResidential').val('');
		$('#noOfClosetsNonResidential').val('');
	}
	
	function validateSewerageConnection() {
		propertyID=$('#propertyIdentifier').val()
		if(propertyID != '') {
			$.ajax({
				url: "/stms/ajaxconnection/check-connection-exists",      
				type: "GET",
				data: {
					propertyID : propertyID  
				},
				dataType: "json",
				success: function (response) { 
					if(response != '') {
						
						if($('#legacy'))
						{
							var radioValue = $("input[name='applicationType']:checked").val();
							 $('#frm input[type="radio"]').each(function(){
							      $(this).checked = false;  
							  });
							 $('input[type="radio"]').val=radioValue;
				           if(radioValue==2)
							 loadPropertyDetails();
							else{
								resetPropertyDetails();
								bootbox.alert(response);
							}
						}
						else
						{	
							resetPropertyDetails();
							bootbox.alert(response);
						}
					}
					else {
						loadPropertyDetails();
					}
				}, 
				error: function (response) {
					resetPropertyDetails();
					bootbox.alert("connection validation failed");
				}
			});
		}		
	}
});


function loadPropertyDetails() {
	propertyID=$('#propertyIdentifier').val()
	allowIfPTDueExists = $('#allowIfPTDueExists').val() 
	var errorMessage=""; 
	if(propertyID != '') {
		$.ajax({
			url: "/ptis/rest/property/"+propertyID,      
			type: "GET",
			dataType: "json",
			success: function (response) { 
				var waterTaxDue = getWaterTaxDue(propertyID);
				//console.log(waterTaxDue['CURRENTWATERCHARGE']);  
				if(response.errorDetails.errorCode != null && response.errorDetails.errorCode != '') {
					if($('#legacy'))
					{
				    resetPropertyDetails();
					}
					bootbox.alert(response.errorDetails.errorMessage);
				}
				else {	
					if(allowIfPTDueExists=='false' && response.propertyDetails.taxDue > 0) {
						resetPropertyDetails();
						errorMessage = "Property tax is due with Rs. "+response.propertyDetails.taxDue+"/- for the assessment no "+propertyID+", please pay the due amount to create new Sewerage connection";
					}
					if(waterTaxDue['WATERTAXDUE'] > 0) {
						errorMessage += "Water tax is due with Rs. "+waterTaxDue['WATERTAXDUE']+"/- for the assessment no "+propertyID+", please pay the due amount to create new Sewerage connection";
					}
					if((allowIfPTDueExists=='false' && response.propertyDetails.taxDue > 0) || waterTaxDue['WATERTAXDUE'] > 0) {
						bootbox.alert(errorMessage);
					}
					else {					
						$('#propertyIdentifierError').html('');
						applicantName = '';
						for(i=0; i<response.ownerNames.length; i++) {
							if(applicantName == '')
								applicantName = response.ownerNames[i].ownerName;
							else 							
								applicantName = applicantName+ ', '+response.ownerNames[i].ownerName;
						}
						$("#applicantname").val(applicantName);
						$("#nooffloors").val(response.propertyDetails.noOfFloors);
						if(response.ownerNames[0].mobileNumber != '')
							$("#mobileNumber").val(response.ownerNames[0].mobileNumber);
						if(response.ownerNames[0].emailId != '')
							$("#email").val(response.ownerNames[0].emailId);
						$("#propertyaddress").val(response.propertyAddress);
						boundaryData = '';
						if(response.boundaryDetails.zoneName != null && response.boundaryDetails.zoneName != '')
							boundaryData = response.boundaryDetails.zoneName;
						if(response.boundaryDetails.wardName != null && response.boundaryDetails.wardName != '') {
							if(boundaryData == '')
								boundaryData = response.boundaryDetails.wardName;
							else
								boundaryData = boundaryData + " / " + response.boundaryDetails.wardName;
						}
						if(response.boundaryDetails.blockName != null && response.boundaryDetails.blockName != '') {
							if(boundaryData == '')
								boundaryData = response.boundaryDetails.blockName;
							else
								boundaryData = boundaryData + " / " +response.boundaryDetails.blockName; 
						}
						$("#aadhaar").val(response.ownerNames[0].aadhaarNumber);
						$("#locality").val(response.boundaryDetails.localityName);
						$("#zonewardblock").val(boundaryData);
						$("#propertytax").val(response.propertyDetails.currentTax);
						$('#watercharges').val(waterTaxDue['CURRENTWATERCHARGE']);
					}
				}					
			}, 
			error: function (response) {
				console.log("failed");
			}
		});
	}		
}

function resetPropertyDetails() {
	$('#propertyIdentifier').val('');
	$('#applicantname').val('');
	$('#mobileNumber').val('');
	$('#email').val('');
	$('#aadhaar').val('');	
	$('#propertyaddress').val('');
	$('#locality').val('');
	$('#zonewardblock').val('');
	$('#nooffloors').val('');
	$('#propertytax').val('0.00');
	$('#watercharges').val('0.00');

}

function getWaterTaxDue(propertyID) {
	var result;
	if(propertyID != "") {
		$.ajax({
			url: "/stms/ajaxconnection/check-watertax-due",
				type: "GET",
				'async':false,
				cache: true,
				data: {
					assessmentNo : propertyID
				},
				dataType: "json",
		}).done(function(value) {
				result = value; 
		});
		return result;
	}
}