<%@ include file="/includes/taglibs.jsp"%>
<head>
<title><s:text name="cashCollectionReport.title" /></title>
<script>
function clearErrors()
{
	// First clear all error messages 
	dom.get("comparedatemessage").style.display="none";
	dom.get("mandatoryfromdate").style.display="none";
	dom.get("mandatorytodate").style.display="none";
}

function validate()
{
	var fromdate=dom.get("fromDate").value;
	var todate=dom.get("toDate").value;
	var valSuccess = true;

	clearErrors();

	if(fromdate == "")
	{
		dom.get("mandatoryfromdate").style.display="block";
		valSuccess = false;
	}
	
	if(todate == "")
	{
		dom.get("mandatorytodate").style.display="block";
		valSuccess = false;
	}
	
	if(fromdate!="" && todate!="" && fromdate!=todate)
	{
		if(!checkFdateTdate(fromdate,todate))
		{
			dom.get("comparedatemessage").style.display="block";
			valSuccess = false;
		}
	}

	return valSuccess;
}
</script>
</head>
<span align="center" style="display: none" id="mandatoryfromdate">
<li><font size="2" color="red"><b> <s:text
	name="common.datemandatory.fromdate" /> </b></font></li>
</span>
<span align="center" style="display: none" id="mandatorytodate">
<li><font size="2" color="red"><b> <s:text
	name="common.datemandatory.todate" /> </b></font></li>
</span>
<span align="center" style="display: none" id="invaliddateformat">
<li><font size="2" color="red"><b> <s:text
	name="common.dateformat.errormessage" /> </b></font></li>
</span>
<span align="center" style="display: none" id="comparedatemessage">
<li><font size="2" color="red"><b> <s:text
	name="common.comparedate.errormessage" /> </b></font></li>
</span>
<body>
<s:form theme="simple" name="cashCollectionForm"
	action="cashCollectionReport!report.action">
	<div class="formmainbox">
	<div class="subheadnew"><s:text name="cashCollectionReport.title" /></div>
	<div class="subheadsmallnew"><span class="subheadnew"><s:text
		name="collectionReport.criteria" /></span></div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">

		<tr>
			<td width="4%" class="bluebox">&nbsp;</td>
			<td width="21%" class="bluebox"><s:text
				name="collectionReport.criteria.fromdate" /><span class="mandatory">*</span></td>
			<s:date name="fromDate" var="cdFormat" format="dd/MM/yyyy" />
			<td width="24%" class="bluebox"><s:textfield id="fromDate"
				name="fromDate" value="%{cdFormat}"
				onfocus="javascript:vDateType='3';"
				onkeyup="DateFormat(this,this.value,event,false,'3')" /><a
				href="javascript:show_calendar('forms[0].fromDate');"
				onmouseover="window.status='Date Picker';return true;"
				onmouseout="window.status='';return true;"><img
				src="${pageContext.request.contextPath}/images/calendaricon.gif"
				alt="Date" width="18" height="18" border="0" align="absmiddle" /></a>
			<div class="highlight2" style="width: 80px">DD/MM/YYYY</div>
			</td>
			<td width="21%" class="bluebox"><s:text
				name="collectionReport.criteria.todate" /><span class="mandatory">*</span></td>
			<s:date name="toDate" var="cdFormat1" format="dd/MM/yyyy" />
			<td width="30%" class="bluebox"><s:textfield id="toDate"
				name="toDate" value="%{cdFormat1}"
				onfocus="javascript:vDateType='3';"
				onkeyup="DateFormat(this,this.value,event,false,'3')" /><a
				href="javascript:show_calendar('forms[0].toDate');"
				onmouseover="window.status='Date Picker';return true;"
				onmouseout="window.status='';return true;"><img
				src="${pageContext.request.contextPath}/images/calendaricon.gif"
				alt="Date" width="18" height="18" border="0" align="absmiddle" /></a>
			<div class="highlight2" style="width: 80px">DD/MM/YYYY</div>
			</td>
		</tr>
		<tr>
			<td width="4%" class="bluebox2">&nbsp;</td>
			<td width="21%" class="bluebox2"><s:text
				name="collectionReport.criteria.counter" /></td>
			<td width="30%" class="bluebox2"><s:select headerKey="-1"
				headerValue="%{getText('collectionReport.counter.all')}"
				name="counterId" id="counter" cssClass="selectwk"
				list="dropdownData.counterList" listKey="id" listValue="name"
				value="%{counterId}" /></td>
			<td width="21%" class="bluebox2"><s:text
				name="collectionReport.criteria.user" /></td>
			<td width="30%" class="bluebox2"><s:select headerKey="-1"
				headerValue="%{getText('collectionReport.user.all')}" name="userId"
				id="user" cssClass="selectwk" list="dropdownData.receiptCreatorList"
				listKey="id" listValue="userName" value="%{userId}" /></td>
		</tr>
		<tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="collectionReport.criteria.zone"/></td>
	      <td width="30%" class="bluebox"><s:select headerKey="-1" headerValue="%{getText('collectionReport.zone.all')}" name="boundaryId" id="boundaryId" cssClass="selectwk" list="dropdownData.activeZoneList" listKey="id" listValue="bndryNameLocal" value="%{boundaryId}" /> </td>
		  <td width="21%" class="bluebox">&nbsp;</td>
	      <td width="30%" class="bluebox">&nbsp;</td>
	    </tr>

	</table>
<div align="left" class="mandatorycoll"><s:text name="common.mandatoryfields"/></div>
	</div>
	
	<div class="buttonbottom">
			<label>
				<s:submit type="submit" cssClass="buttonsubmit" id="button"
					value="%{getText('collectionReport.create')}"
					onclick="return validate();" />
			</label>&nbsp;
			<label>
				<s:reset type="submit" cssClass="button"
					value="%{getText('collectionReport.reset')}"
					onclick="return clearErrors();" />
			</label>&nbsp;
			<label>
				<input type="button" class="button" id="buttonClose"
					value="<s:text name='common.buttons.close'/>"
					onclick="window.close()" />
			</label>			
		</div>

</s:form>
</body>
</html>
