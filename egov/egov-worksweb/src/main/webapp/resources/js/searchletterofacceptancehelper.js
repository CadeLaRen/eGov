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
jQuery('#btnsearch').click(function(e) {
	var fromDate = $('#fromDate').data('datepicker').date;
	var toDate = $('#toDate').data('datepicker').date;
	var flag = true; 
	if(toDate != '' && fromDate != '') {
		if(fromDate > toDate) {
			flag = false;
			bootbox.alert('To Date should be greater than From Date');
		}
	}
	if(flag)
	callAjaxSearch();
});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

function renderPDF(){
	var id = $('#id').val();
	window.open("/egworks/letterofacceptance/letterOfAcceptancePDF/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function renderAction(id, value) {
	if (value == 1)
		window.open("/egworks/letterofacceptance/view/" + id, '',
				'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
	if(value == 2)
		window.open("/egworks/letterofacceptance/letterOfAcceptancePDF/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/egworks/letterofacceptance/ajaxsearch-loa",
					type : "POST",
					"data" : getFormData(jQuery('form'))
				},
				"sPaginationType" : "bootstrap",
				"bDestroy" : true,
				'bAutoWidth': false,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : []
				},
				"fnRowCallback" : function(row, data, index) {
					$('td:eq(0)', row).html(index + 1);
					if (data.estimateNumber != null)
						$('td:eq(3)', row).html(
								'<a href="javascript:void(0);" onclick="openLineEstimate(\''
										+ data.lineEstimateId + '\')">'
										+ data.estimateNumber + '</a>');
					$('td:eq(6)',row).html(parseFloat(Math.round(data.workOrderAmount * 100) / 100).toFixed(2));
					$('td:eq(8)', row)
							.html(
									'<select id="actionDropdown" class="form-control" onchange="renderAction('
											+ data.id
											+ ', this.value)"><option value="">Select from below</option><option value="1">View LOA</option><option value="2">View PDF</option></select>');
					return row;
				},
				aaSorting : [],
				columns : [ {
					"data" : "",
					"sClass" : "text-center","width": "2%"
				}, {
					"data" : "workOrderNumber",
					"sClass" : "text-left","autoWidth": "false"
				}, {
					"data" : "workOrderDate",
					"sClass" : "text-left" ,"width": "6%",
					render: function (data, type, full) {
						if(full!=null &&  full.workOrderDate != undefined) {
							var regDateSplit = full.workOrderDate.split("T")[0].split("-");		
							return regDateSplit[2] + "/" + regDateSplit[1] + "/" + regDateSplit[0];
						}
						else return "";
			    	}
				}, {
					"data" : "estimateNumber",
					"sClass" : "text-left","autoWidth": "false"
				}, {
					"data" : "nameOfWork",
					"sClass" : "text-left","autoWidth": "false"
				}, {
					"data" : "contractor",
					"sClass" : "text-left","autoWidth": "false"
				}, {
					"data" : "workOrderAmount","width": "6%",
					"sClass" : "text-right"
				}, {
					"data" : "status",
					"sClass" : "text-center","autoWidth": "false"
				}, {
					"data" : "",
					"sClass" : "text-left","width": "7%"
				} ]
			});
}

function openLineEstimate(lineEstimateId) {
	window.open("/egworks/lineestimate/view/" + lineEstimateId, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

$(document).ready(function() {
	var estimateNumber = new Bloodhound({
		datumTokenizer : function(datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		remote : {
			url : '/egworks/letterofacceptance/ajaxestimatenumbers?name=%QUERY',
			filter : function(data) {
				return $.map(data, function(ct) {
					return {
						name : ct
					};
				});
			}
		}
	});

	estimateNumber.initialize();
	var estimateNumber_typeahead = $('#estimateNumber').typeahead({
		hint : true,
		highlight : true,
		minLength : 3
	}, {
		displayKey : 'name',
		source : estimateNumber.ttAdapter()
	});
});

$(document)
		.ready(
				function() {
					var contractorSearch = new Bloodhound(
							{
								datumTokenizer : function(datum) {
									return Bloodhound.tokenizers
											.whitespace(datum.value);
								},
								queryTokenizer : Bloodhound.tokenizers.whitespace,
								remote : {
									url : '/egworks/letterofacceptance/ajaxsearchcontractors-loa?name=%QUERY',
									filter : function(data) {
										return $.map(data, function(ct) {
											return {
												name : ct,
											};
										});
									}
								}
							});

					contractorSearch.initialize();
					var contractorSearch_typeahead = $('#contractorSearch')
							.typeahead({
								hint : true,
								highlight : true,
								minLength : 3
							}, {
								displayKey : 'name',
								source : contractorSearch.ttAdapter()
							}).on('typeahead:selected', function(event, data) {
								$("#contractorCode").val(data.code);
								$("#contractor").val(data.value);
							});
				});

$(document).ready(function() {
	var workOrderNumber = new Bloodhound({
		datumTokenizer : function(datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		remote : {
			url : '/egworks/letterofacceptance/ajaxloanumber?name=%QUERY',
			filter : function(data) {
				return $.map(data, function(ct) {
					return {
						name : ct
					};
				});
			}
		}
	});

	workOrderNumber.initialize();
	var workOrderNumber_typeahead = $('#workOrderNumber').typeahead({
		hint : true,
		highlight : true,
		minLength : 3
	}, {
		displayKey : 'name',
		source : workOrderNumber.ttAdapter()
	});
});