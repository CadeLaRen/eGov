$(document).ready(function(){
	 
   var agency = new Bloodhound({
		datumTokenizer: function (datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: {
			url: '/adtax/agency/agencies?name=%QUERY',
			filter: function (data) {
				return $.map(data, function (ct) {
					return {
						name: ct.name,
						value: ct.id
					};
				});
			}
		}
	});
	
   agency.initialize(); // Instantiate the Typeahead UI
	
	$('.typeahead').typeahead({
		  hint: true,
		  highlight: true,
		  minLength: 1
		}, {
		displayKey: 'name',
		source: agency.ttAdapter()
	}).on('typeahead:selected typeahead:autocompleted typeahead:matched', function(event, data){
		$("#agencyId").val(data.value);    
   });
   
   $('.add-attachment').click(function(){
       console.log('came');
       $(this).parent().before('<div class="col-sm-3 add-margin"> <input type="file" class="form-control" required> </div>');
   });
   
   $('#measurement').change(function(){
	   calculateTax();
	});
   $('#subCategory').change(function(){
	   calculateTax();
	});
   $('#unitOfMeasure').change(function(){
	   calculateTax();
	});
   $('#rateClass').change(function(){
	   calculateTax();
	});
   
   $('#propertyNumber').change(function(){
	   callPropertyTaxRest(); 
	});
   
   $('#location').change(function() {
	//	 alert('HI');
		 populateBoundaries();
	 });
   $('#ward').change(function() {
		   populateBlock();
		 });
   
   $('#category').change(function(){
	   
	   if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/subcategories",
				cache: true,
				dataType: "json",
				data:{'categoryId' : this.value}
			}).done(function(value) {
				$('#subCategory option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#subCategory').append($('<option>').text(val.description).attr('value', val.id));
				});
				if(subcategory !== '') {
					$("select#subCategory").val(subcategory); 
					subcategory = '';
				}
			});
		}
	});
   
   $('#adminBoundryParent').change(function(){
		if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/child-boundaries",
				cache: true,
				dataType: "json",
				data:{'parentBoundaryId' : this.value}
			}).done(function(value) {
				$('#ward option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#ward').append($('<option>').text(val.name).attr('value', val.id));
				});
				if(ward !== '') {
					$("select#ward").val(ward); 
					ward = '';
				}
			});
		}
	});
   
   $('#revenueBoundaryParent').change(function(){
		if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/child-boundaries",
				cache: true,
				dataType: "json",
				data:{'parentBoundaryId' : this.value}
			}).done(function(value) {
				$('#location option:gt(0)').remove();
				$.each(value, function(index, val) {
				     $('#location').append($('<option>').text(val.name).attr('value', val.id));
				});
				if(location !== '') {
					$("select#location").val(location); 
					location = '';
				}
			});
		}
	});
   
   $('input[type=file]').on('change.bs.fileinput',function(e) {
	   EXIF.getData(e.target.files[0], function() {
		   if (EXIF.getTag(this, "GPSLatitude")) {
				var imagelat = EXIF.getTag(this, "GPSLatitude"),
				imagelongt = EXIF.getTag(this, "GPSLongitude");
				var formatted_lat = format_lat_long(imagelat.toString());
				var formatted_long = format_lat_long(imagelongt.toString());
				$.ajax({
					type: "POST",
					url: 'https://maps.googleapis.com/maps/api/geocode/json?latlng='+formatted_lat+','+formatted_long+'&sensor=true',
					dataType: 'json',
					success : function(data){
						$('#latitude').val(formatted_lat);
						$('#longitude').val(formatted_long);
					}
				});
		   }
	   });
   });
   
   function resetOnPropertyNumChange(){

    	$('#ward').html("");
    	$('#location').val("");
		$('#block').html("");
		$('#street').html("");
		$('#address').val("");
		
   }
   
   function callPropertyTaxRest(){
	var propertyNo = jQuery("#propertyNumber").val();
   	if(propertyNo!="" && propertyNo!=null){
			console.log(propertyNo); 
			jQuery.ajax({
			//	url: "/ptis/rest/property/" + propertyNo,
				url:"/restapi/property/assessmentDetails",
				type:"post",
				contentType:"application/json", 
				dataType :"json",
			    data: JSON.stringify({"assessmentNo":propertyNo}),
				success:function(data){
					if(data.errorDetails.errorCode != null && data.errorDetails.errorCode != ''){
						alert(data.errorDetails.errorMessage);
						$('#location').val("");
						
						document.getElementById("propertyNumber").value="";
						resetOnPropertyNumChange();
					} else{
						if(data.boundaryDetails!=null){
							
							$('#ward').html("");
							$('#block').html("");
							$('#street').html("");
							$('#address').val("");
							
							$('#location').val(data.boundaryDetails.localityId);
							$('#ward').append("<option value='"+data.boundaryDetails.wardId+"'>"+data.boundaryDetails.wardName+"</option>");
							$('#block').append("<option value='"+data.boundaryDetails.blockId+"'>"+data.boundaryDetails.blockName+"</option>");
							if(data.boundaryDetails.streetId!=null)
							$('#street').append("<option value='"+data.boundaryDetails.streetId+"'>"+data.boundaryDetails.streetName+"</option>");
					
							if(data.propertyAddress!=null)
							$('#address').val(data.propertyAddress); 
						}
					}
				},
				error:function(e){
					console.log('error:'+e.message);
					document.getElementById("propertyNumber").value="";
					resetOnPropertyNumChange();
					alert("Error getting property details");
				}
			});
   	} else{
   		document.getElementById("propertyNumber").focus();
   		resetOnPropertyNumChange();
       }
   }
   function populateBoundaries() {
		//alert('HI0000000000');
		console.log("came jursidiction"+$('#location').val());
		$.ajax({
			type: "GET",
			url: "/egi/boundary/ajaxBoundary-blockByLocality.action",
			cache: true,
			dataType: "json",
			data:{
				locality : $('#location').val()
		  	   }
			}).done(function(response) {

				$('#ward').html("");
				$('#block').html("");
				$('#street').html("");
				$.each(response.results.boundaries, function (j, boundary) {
					if (boundary.wardId) {
						$('#ward').append("<option value='"+boundary.wardId+"'>"+boundary.wardName+"</option>");
					}
					$('#block').append("<option value='"+boundary.blockId+"'>"+boundary.blockName+"</option>");
				});
				$.each(response.results.streets, function (j, street) {
					$('#street').append("<option value='"+street.streetId+"'>"+street.streetName+"</option>");
				});
			 })
			.fail(function(response1) {
				console.log("failed");
				$('#ward').html("");
				$('#block').html("");
				$('#street').html("");
				alert("No boundary details mapped for location");
			 });
			
	
   }
   function populateBlock()
   {
	   	$.ajax({
		type: "GET",
		url: "/egi/boundary/ajaxBoundary-blockByWard.action",
		cache: true,
		dataType: "json",
		data:{
			wardId : $('#ward').val()
				}
		}).done(function(response) {

			$('#block').html("");
			$.each(response, function (j, block) {
				$('#block').append("<option value='"+block.blockId+"'>"+block.blockName+"</option>");
			});
		 })
		.fail(function(response1) {
			console.log("failed");
			$('#block').html("");
			alert("No block details mapped for ward");
		 });
	
   }

   function format_lat_long(latorlong) {
		var loc_arry = latorlong.split(",");
		var degree= parseFloat(loc_arry[0]);
		var minutes= parseFloat(loc_arry[1]);
		var seconds= parseFloat(loc_arry[2]);
		var formatted = degree+((minutes*60)+seconds)/3600;
		
		return formatted;
	}
   
   function calculateTax()
   {
	      
	   if ($('#rateClass').val() === '' || $('#unitOfMeasure').val() === '' ||  $('#subCategory').val() === '' || $('#measurement').val() === '') {
			return;
		} else {
		
		//	alert('All fields are entered');	
			$.ajax({
				type: "GET",
				url: "/adtax/hoarding/calculateTaxAmount",
				cache: true,
				dataType: "json",
				data:{
					'unitOfMeasureId' : $('#unitOfMeasure').val(),
					'measurement' : $('#measurement').val(),
					'subCategoryId' : $('#subCategory').val(),
					'rateClassId' : $('#rateClass').val()
					}
			}).done(function(value) {
				//alert(value);
				$('#taxAmount').val(value); 
			
			});
		}
	
   }
   
   $('#category').trigger('change');
   $('#location').trigger('change');
   $('#revenueBoundryParent').trigger('change');
});