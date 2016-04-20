/*#-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
# accountability and the service delivery of the government  organizations.
#   
#  Copyright (C) <2015>  eGovernments Foundation
#   
#  The updated version of eGov suite of products as by eGovernments Foundation 
#  is available at http://www.egovernments.org
#   
#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  any later version.
#   
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#   
#  You should have received a copy of the GNU General Public License
#  along with this program. If not, see http://www.gnu.org/licenses/ or 
#  http://www.gnu.org/licenses/gpl.html .
#   
#  In addition to the terms of the GPL license to be adhered to in using this
#  program, the following additional terms are to be complied with:
#   
# 1) All versions of this program, verbatim or modified must carry this 
#    Legal Notice.
#   
# 2) Any misrepresentation of the origin of the material is prohibited. It 
#    is required that all modified versions of this material be marked in 
#    reasonable ways as different from the original version.
#   
# 3) This license does not grant any rights to any user of the program 
#    with regards to rights under trademark law for use of the trade names 
#    or trademarks of eGovernments Foundation.
#   
# In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#-------------------------------------------------------------------------------*/
var BANKENTRIESNOTINBANKBOOKLIST = 'bankEntriesNotInBankBookList';
var bankEntriesNotInBankBookTableIndex = 0;
var bankEntriesNotInBankBooksTable;
function createTextFieldFormatterBENIBB(prefix, suffix) {
	return function(el, oRecord, oColumn, oData) {
		var value = (YAHOO.lang.isValue(oData)) ? oData : "";
		el.innerHTML = "<input type='type' id='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "' name='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "' style='width:90px;' />";
	}
}

function createAmountFieldFormatterBENIBB(prefix, suffix) {
	return function(el, oRecord, oColumn, oData) {
		var value = (YAHOO.lang.isValue(oData)) ? oData : "";
		el.innerHTML = "<input type='text'  id='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "'  name='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "' style='text-align:right;width:90px;'/>";

	}
}

function createHiddenFieldFormatterBENIBB(prefix, suffix) {
	return function(el, oRecord, oColumn, oData) {
		el.innerHTML = "<input type='text' id='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "' name='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix + "'/>";
	}
}

function createCheckBoxFormatterBENIBB(prefix, suffix) {
	return function(el, oRecord, oColumn, oData) {
		var value = (YAHOO.lang.isValue(oData)) ? oData : "";
		el.innerHTML = "<input type='checkbox' id='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "' name='" + prefix + "["
				+ bankEntriesNotInBankBookTableIndex + "]" + suffix
				+ "'  style='width:90px;' onclick='setValue(this)' />";
	}
}

function createDateFormatterBENIBB(prefix, suffix) {
	return function(el, oRecord, oColumn, oData) {
		var value = (YAHOO.lang.isValue(oData)) ? oData : "";
		el.innerHTML = '<input type="text" id="'
				+ prefix
				+ '['
				+ bankEntriesNotInBankBookTableIndex
				+ ']'
				+ suffix
				+ '"	name="'
				+ prefix
				+ '['
				+ bankEntriesNotInBankBookTableIndex
				+ ']'
				+ suffix
				+ '" data-date-end-date="0d" onkeyup="DateFormat(this,this.value,event,false,\'3\')"	placeholder="DD/MM/YYYY" class="form-control datepicker" data-inputmask="\'mask\': \'d/m/y\'"  />';
	}
}

function createDropdownFormatterBENIBB(prefix) {
	return function(el, oRecord, oColumn, oData) {
		var selectedValue = (lang.isValue(oData)) ? oData : oRecord
				.getData(oColumn.field), options = (lang
				.isArray(oColumn.dropdownOptions)) ? oColumn.dropdownOptions
				: null, selectEl, collection = el
				.getElementsByTagName("select");
		if (collection.length === 0) {
			selectEl = document.createElement("select");
			selectEl.className = YAHOO.widget.DataTable.CLASS_DROPDOWN;
			selectEl.name = prefix + '[' + bankEntriesNotInBankBookTableIndex
					+ '].' + oColumn.getKey();
			selectEl.id = prefix + '[' + bankEntriesNotInBankBookTableIndex
					+ '].' + oColumn.getKey();
			// selectEl.onfocus=check;
			selectEl = el.appendChild(selectEl);
			var selectedIndex = {
				value : bankEntriesNotInBankBookTableIndex
			};

			YAHOO.util.Event.addListener(selectEl, "change", onDropdownChange,
					selectedIndex, this);

		}

		selectEl = collection[0];

		if (selectEl) {
			selectEl.innerHTML = "";
			if (options) {
				for (var i = 0; i < options.length; i++) {
					var option = options[i];
					var optionEl = document.createElement("option");
					optionEl.value = (lang.isValue(option.value)) ? option.value
							: option;
					optionEl.innerHTML = (lang.isValue(option.text)) ? option.text
							: (lang.isValue(option.label)) ? option.label
									: option;
					optionEl = selectEl.appendChild(optionEl);
					if (optionEl.value == selectedValue) {
						optionEl.selected = true;
					}
				}
			} else {
				selectEl.innerHTML = "<option selected value=\""
						+ selectedValue + "\">" + selectedValue + "</option>";
			}
		} else {
			el.innerHTML = lang.isValue(oData) ? oData : "";
		}
	}
}
function updateBENIBBTableIndex() {

	bankEntriesNotInBankBookTableIndex = bankEntriesNotInBankBookTableIndex + 1;
}

function setValue(obj) {
	console.log("inside fun");
	if (obj.checked) {
		console.log("true");
		obj.value = true;
	}else{
		obj.value = false;
	}
}