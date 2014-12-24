<%@ page language="java"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ page import="java.util.*,
		org.apache.log4j.Logger,org.egov.infstr.utils.EgovMasterDataCaching,
		org.egov.commons.EgPartytype,org.egov.commons.EgwTypeOfWork,
		java.text.DecimalFormat,org.egov.lib.deduction.client.DocumentTypeForm"
%>

<html>
<head>
	<title>DocumentType Master Setup</title>

<%  
DocumentTypeForm dtform=(DocumentTypeForm)request.getAttribute("DocumentTypeForm"); 
%>
<script>
var myrowId;
function trimTextString(obj,value)
{
    value = value;
    if(value!=undefined)
   {
	   while (value.charAt(value.length-1) == " ")
	   {
		value = value.substring(0,value.length-1);
	   }
	   while(value.substring(0,1) ==" ")
	   {
		value = value.substring(1,value.length);
	   }
	   obj.value = value;
	}
   return value ;
}
function ButtonPress(arg)
{		
	if(!validateDocumentTypeForm(document.DocumentTypeForm))
		return; 
		
	if(document.DocumentTypeForm.partyTypeId.options[document.DocumentTypeForm.partyTypeId.selectedIndex].value==0)
	{
		alert("Select Applied To !!!!");
		return false;
	}
		
	if(document.DocumentTypeForm.parentId.options[document.DocumentTypeForm.parentId.selectedIndex].value!=0)
	{
		var fieldvaluetemp = document.DocumentTypeForm.code.value;
		var codeVal =trimTextString(document.DocumentTypeForm.code,fieldvaluetemp);
		
		if(codeVal != "")
		{
		var parentCode=document.DocumentTypeForm.parentId.options[document.DocumentTypeForm.parentId.selectedIndex].text;
			if(parentCode==codeVal)
			{
				alert("Code and Parent Code cannot be same!!!!");
				return false;
			}
		
		}
		
	}
	if(document.DocumentTypeForm.partyTypeId.options[document.DocumentTypeForm.partyTypeId.selectedIndex].value!=0)
	{
		var fieldvaluetemp = document.DocumentTypeForm.code.value;
		var codeVal =trimTextString(document.DocumentTypeForm.code,fieldvaluetemp);
		
		if(codeVal != "")
		{
		var appliedToCode=document.DocumentTypeForm.partyTypeId.options[document.DocumentTypeForm.partyTypeId.selectedIndex].text;
			if(appliedToCode==codeVal)
			{
				alert("Code and Applied To cannot be same!!!!");
				return false;
			}

		}
			
	}
	
	document.getElementById("button").value=arg;
	var mode="${mode}";
	if(mode == "create")
	{					
		document.DocumentTypeForm.action = "../deduction/DocumentTypeMaster.do?submitType=createDocumentType";
		document.DocumentTypeForm.submit();		
	}
	if(mode != "create")
	{			
		document.DocumentTypeForm.action = "../deduction/DocumentTypeMaster.do?submitType=modifyDocumentType";
		document.DocumentTypeForm.submit();		
	}
}
function uniqueCheckForDocumentCode()
{
	<% 
	if(dtform.getCode()==null)
	{%>

		// For create Mode
		booleanValue=uniqueCheckingBoolean('../commonyui/egov/uniqueCheckAjax.jsp', 'egw_typeofwork', 'CODE', 'code', 'no', 'no');
			if(booleanValue==false)
			{
				alert("This Code already used for some other Document Type!!!!");
				return false;
			}
	<%
	}
	else
	{
	%>
		// For Modify Mode
		var documentCodeNew=document.getElementById('code').value;
		var documentCodeOld="<%=(dtform.getCode())%>";

		if(documentCodeNew!=documentCodeOld)
		{
			booleanValue=uniqueCheckingBoolean('../commonyui/egov/uniqueCheckAjax.jsp', 'egw_typeofwork', 'CODE', 'code', 'no', 'no');

			if(booleanValue==false)
			{
			alert("This Code already used for some other Document Type!!!!");
			return false;
			}		
		}	

	<%
	}
	%>
}

function setDefault()
{
	<% 
		ArrayList parentCodeList=(ArrayList)EgovMasterDataCaching.getInstance().get("egi-typeOfWorkParent");
		//ArrayList appliedToList=(ArrayList)EgovMasterDataCaching.getInstance().get("egi-partyTypeAllChild");
		ArrayList appliedToList=(ArrayList)EgovMasterDataCaching.getInstance().get("egi-partyTypeMaster");
	%>
	
	var target='<%=(request.getAttribute("alertMessage"))%>';
	if(target!="null")
	{
		alert('<%=request.getAttribute("alertMessage")%>');
	}
	
	var buttonType="${buttonType}";		
	if(buttonType == "saveclose")
		window.close();
			
	var mode="${mode}";
	if(mode == "create")
	{		
		document.getElementById("row2").style.display="block";	
		document.getElementById("row3").style.display="none";
		document.getElementById("row4").style.display="none";		
	}
	
	if (target!="null")
	
	{
	 	window.location= "../deduction/DocumentTypeMaster.do?submitType=beforeCreate";		
	}
	
	if(mode == "modify")
	{
		document.title='Modify Document Type';
	//	document.getElementById('screenName').innerHTML='Modify Document Type';
		document.getElementById("row2").style.display="none";	
		document.getElementById("row3").style.display="block";
		document.getElementById("row4").style.display="none";		
	}
	if(mode == "view")
	{
		document.title='View Document Type';
		//document.getElementById('screenName').innerHTML='View Document Type';
		document.getElementById("row4").style.display="block";
		document.getElementById("row2").style.display="none";	
		document.getElementById("row3").style.display="none";	
		
		for(var i=0;i<document.DocumentTypeForm.length;i++)
		{
			if(document.DocumentTypeForm.elements[i].value != "Close")
			{
				document.DocumentTypeForm.elements[i].disabled =true;
			}					
		}
	}
}

function onClickCancel()
{
	document.DocumentTypeForm.reset();
}	


</script>
</head>
<body onload="setDefault()">

<html:form  action="/deduction/DocumentTypeMaster.do" >
<table align='center' class="tableStyle"> 
 

<tr>
<html:hidden property="id" />
<td class="txt" align="right" >Code<SPAN class="leadon">*&nbsp;</SPAN></td>
<td class="fieldcell"><html:text onblur="uniqueCheckForDocumentCode();" property= "code" maxlength="20"/></td> 

<td height="43" class="txt" align="right" >Parent&nbsp;Code</td>
<td class="smallfieldcell"  width="25%" height="43">
	 <html:select property="parentId" styleClass="combowidth" style="width:150px">
		<html:option value='0'>--Choose--</html:option>
		<c:forEach var="twork" items="<%=parentCodeList%>" > 
		<html:option value="${twork.id}">${twork.name}</html:option> 
	</c:forEach>
</html:select> 
</td>
</tr>
<tr>
<td height="43" class="txt" align="right" >Applied To<SPAN class="leadon">*&nbsp;</SPAN></td>
<td class="smallfieldcell"  width="25%" height="43">
	 <html:select property="partyTypeId" styleClass="combowidth" style="width:150px">
		<html:option value='0'>--Choose--</html:option>
		<c:forEach var="ptype" items="<%=appliedToList%>" > 
		<html:option value="${ptype.id}">${ptype.name}</html:option> 
	</c:forEach>
    	</html:select> </td>
<td></td>
<td></td>
</tr>

<tr>
<td class="labelcell" align="right" width="25%">Description<SPAN class="leadon">*&nbsp;</SPAN></td>
<td class="smallfieldcell" align="center" width="25%"><html:textarea property="description" styleClass="combowidthforGLCode" /></td>
</tr>
<tr>
<td>

<tr><td>&nbsp;</td></tr> 
<tr  id="row2" name="row2">
<td  align="center" colspan=4>
<input type=hidden name="button" id="button"/>
<html:button styleClass="button" value="Save & Close" property="b2" onclick="ButtonPress('saveclose')" />
<html:button styleClass="button" value="Save & New" property="b4" onclick="ButtonPress('savenew')" />
<html:reset styleClass="button" value="Cancel" property="b1" onclick="onClickCancel()" />
<html:button styleClass="button" value="Close" property="b3" onclick="window.close();" />
</td>
</tr>
<tr style="DISPLAY: none" id="row3" name="row3">
<td  align="center" colspan=4>
<html:button styleClass="button" value=" Save " property="b4" onclick="ButtonPress('saveclose')" />
<html:button styleClass="button" value="Close" property="b3" onclick="window.close();" />
</td>
</tr>
<tr style="DISPLAY: none" id="row4" name="row4">
<td  align="center" colspan=4>
<html:button styleClass="button" value="Close" property="b3" onclick="window.close();" />
</td>
</tr>

</td>
</tr>
</table>
<html:javascript formName="DocumentTypeForm"/> 
</html:form>

</body>
</html>	