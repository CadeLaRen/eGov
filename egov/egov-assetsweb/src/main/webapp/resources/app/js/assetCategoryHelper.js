$(document).ready(function(){

	$('#parent').change(function() {
		var parentId = jQuery('#parent').val();
		console.log(parentId);
		if(parentId === '')
		{
			$('#assetAccountCode').val("");
			$('#accDepAccountCode').val("");
			$('#revAccountCode').val("");
			$('#depExpAccountCode').val("");
			$('#uom').val("");
		}
		else
		{
			$.ajax({
				url: '/egassets/assetcategory/getParentAccounts/'+ parentId,
				type: "GET",
				dataType: "json", 
				success: function(response) {
					var stringArray = response.split(",");
					$.each(stringArray, function(index, value) {
						var array = value.split(":");
						if(array[1] !== "")
							$('#'+array[0]).val(array[1]);
					});
				},
				error: function(response){
					console.log("Failed");
				}
			});
		}
	});

	$('#btnsearch').click(function(e) {
		callAjaxSearch();
	});

});



function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}



function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
	.dataTable({
		ajax : {
			url : "/egassets/assetcategory/ajaxsearch/"
				+ $('#mode').val(),
				type : "POST",
				"data" : getFormData(jQuery('form'))
		},
		"fnRowCallback" : function(row, data, index) {
			$(row).on(
					'click',
					function() {
						console.log(data.id);
						window.open('/egassets/assetcategory/'
								+ $('#mode').val() + '/' + data.id, '',
						'width=800, height=600');
					});
		},
		"sPaginationType" : "bootstrap",
		"bDestroy" : true,
		"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
		"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
		"oTableTools" : {
			"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
			"aButtons" : [ "xls", "pdf", "print" ]
		},
		aaSorting : [],
		columns : [ {
			"data" : "code",
			"sClass" : "text-left"
		}, {
			"data" : "name",
			"sClass" : "text-left"
		}, {
			"data" : "assetType",
			"sClass" : "text-left"
		}, {
			"data" : "parent",
			"sClass" : "text-left"
		}, {
			"data" : "uom",
			"sClass" : "text-left"
		} ]
	});
}