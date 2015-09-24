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



		function calculateAmount(obj){
	
	 var table= document.getElementById("dcbOnlinePaymentTable");
	 var rowobj= getRow(obj).rowIndex;
		/*if(obj.value !=undefined)
		{
			if( obj.value -(document.forms[0].actualCollection[rowobj].value) < 0 )
				{
				document.forms[0].actualCollection[rowobj].value="";
				return false;
				}
		}*/
	if(document.forms[0].actualAmount[rowobj-2]!=undefined && document.forms[0].actualAmount[rowobj-2].value !=undefined){	 
			for(var j=0;j<=rowobj-2;j++)
			{
				if(document.forms[0].actualAmount[j].value ==""){
					
					alert("Please choose its previos Installments. Random selection not allowed.");
					obj.value="";
					return false;
				}
			}
 		}
	 	
		}
		

		function calculateCollectionAmount(obj){
	
	 var table= document.getElementById("dcbOnlinePaymentTable");
	 var rowobj= getRow(obj).rowIndex;
	if(obj.value !=undefined)
	{
		/*var rate = $(obj).parent('td').prev('td').text();
		var values=$(obj).parent('td').prev('td').attr("value");
		if(document.forms[0].actualAmount[rowobj].value=="")
		{
			alert("Please Enter Demand Amount for This row");
			obj.value="";
			return false;
			//need to fix .. get previous td value n do validation
		}*/
		
		/*if((document.forms[0].actualAmount[rowobj].value) -(obj.value) <0 )
			{
			alert(" Collection Amount should not be greater than Demand amount");
			obj.value="";
			return false;
			}*/
	}
	if(document.forms[0].actualCollection[rowobj-2]!=undefined && document.forms[0].actualCollection[rowobj-2].value !=undefined){	 
			for(var j=0;j<=rowobj-2;j++)
			{
				if(document.forms[0].actualCollection[j].value == ""){
					alert("Please choose its previos Installments. Random selection not allowed.");
					obj.value="";
					return false;
				}
			}
 		}
		}

	 
	 
