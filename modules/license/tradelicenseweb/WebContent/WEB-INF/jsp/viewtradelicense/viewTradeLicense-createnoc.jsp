<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ taglib prefix="egov" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@page
	import="org.egov.lib.admbndry.CityWebsiteDAO,org.egov.lib.admbndry.CityWebsite"%>
<html>
<head>
<title>Trade License Certificate</title>
<script type="text/javascript">
	function refreshInbox() {
		if (opener && opener.top.document.getElementById('inboxframe')) {
			opener.top.document.getElementById('inboxframe').contentWindow.egovInbox
					.refresh();
		}
	}

	function printNoc() {
		document.getElementById('printDiv').style.display = 'none';
		window.print();
	}
</script>
<%
	String cityUrl = (String) session.getAttribute("cityurl");
	CityWebsiteDAO cityWebsiteDAO = new CityWebsiteDAO();
	CityWebsite cityWebsite = cityWebsiteDAO
			.getCityWebSiteByURL(cityUrl);
	String cityName = cityWebsite.getCityName();
	String logoName = cityWebsite.getLogo();
%>
</head>
<body onload="refreshInbox()">
	<div id="content">
		<center>
			<s:form name="certificateform" action="viewTradeLicense"
				theme="simple">
				<s:token />
				<s:push value="model">
					<table width="85%" height="95%"
						style='margin-left: 10px; margin-right: 5px; font-size: 12px; font-family: Arial Unicode MS; border: none;'>
						<tr>
							<td width="30%" align="center" style="font-size: 15px;"
								colspan="4"><s:text name="license.health.dept" /></td>
						</tr>
						<tr>
							<td colspan="4" align="center"><img
								src="/egi/images/<%=logoName%>" width="91" height="90" alt="" /></td>
						</tr>
						<tr>
							<td colspan="4" align="center"
								style="font-size: 16px; font-weight: bolder;"><%=cityName%>
								<br /> <s:text name="license.noc.title" /> <br /></td>
						</tr>
						<tr>
							<td width="30%" colspan="4" align="center"><s:if
									test="%{#parameters.duplicate}">
									<s:text name="license.certificate.watermark.duplicatenoc" />
								</s:if> <br /></td>
						</tr>
						<tr>
							<td width="30%" colspan="2"><s:text name="license.no" /> <b><s:property
										value="nocNumber" /></b></td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<td></td>
							<td width="30%" colspan="2"><s:text name="license.date" />
								:</td>

						</tr>
						<tr>
							<td width="30%"><s:text name="license.to" /></td>
						</tr>
						<tr>

							<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp; Shri/ Smt: <b><s:property
										value="licensee.applicantName" /> </b></td>
						</tr>
						<tr>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<b><s:property
										value="licensee.address.houseNo" /> <s:if
										test="licensee.address.streetAddress1!=null">, </s:if> <s:property
										value="licensee.address.streetAddress1" /> <s:if
										test="licensee.address.streetAddress2!=null">, </s:if> <s:property
										value="licensee.address.streetAddress2" />, <s:property
										value="licensee.boundary.parent.name" />, <s:property
										value="licensee.boundary.name" /> <s:if
										test="licensee.address.pinCode!=null">,  </s:if> <s:property
										value="licensee.address.pinCode" /> </b></td>
							<br />
						</tr>

						<tr>
							<br />
							<td colspan="3"><s:text name="license.subject" /> <b><s:property
										value="licensee.applicantName" /> ,&nbsp;<s:property
										value="tradeName.name" /></b></td>

						</tr>


						<tr>
							<br />
							<td colspan="3"><s:text name="license.reference" /> <s:date
									name='applicationDate' id="VTL.applicationDate"
									format='dd/MM/yyyy' /> <s:property
									value="%{VTL.applicationDate}" /> <s:text
									name="license.registration" /></td>

						</tr>
						<tr>
							<td colspan="3" align="justify"><span
								style="width: 90%; color: black">
									<p>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<s:text name="license.detail1" />
										<s:property value="licensee.applicantName" />
										&nbsp; <b><s:property value="tradeName.name" /> </b>&nbsp;
										<s:text name="license.detail2" />
										<b><s:property value="licensee.address.houseNo" /> <s:if
												test="licensee.address.streetAddress1!=null">, </s:if> <s:property
												value="licensee.address.streetAddress1" /> <s:if
												test="licensee.address.streetAddress2!=null">, </s:if> <s:property
												value="licensee.address.streetAddress2" />, <s:property
												value="licensee.boundary.parent.name" />, <s:property
												value="licensee.boundary.name" /> <s:if
												test="licensee.address.pinCode!=null">,  </s:if> <s:property
												value="licensee.address.pinCode" /> </b>
										<s:text name="license.detail3" />
										<b><s:property value="totalHP" /> </b>&nbsp;
										<s:text name="license.detail4" />
									</p> &nbsp;&nbsp;
							</span></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><<s:text
									name="license.detail5" />&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point1" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point2" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point3" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point4" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point5" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point6" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point7" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point8" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point9" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point10" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point11" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point12" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point13" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point14" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point15" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point16" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point17" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point18" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point19" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point20" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify">&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="license.point20a" /> <s:property value="sandBuckets" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify">&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="license.point20b" /> <s:property value="waterBuckets" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify">&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="license.point20c" /> <s:property value="dcpExtinguisher" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify">&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="license.point20d" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify">&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="license.point20e" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point21" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point22" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point23" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point24" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point25" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point26" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point27" /></td>
						</tr>
						<tr>
							<td colspan="3" align="justify"><s:text
									name="license.point28" /></td>
						</tr>

						<div align="center" id="printDiv">
							<table width="30%" align="center">
								<tr>
									<td><input type="button" id="print" value="Print"
										onclick="return printNoc()" /></td>
									<td><input type="button" id="close" value="Close"
										onclick="javascript:window.close();" /></td>
								</tr>
							</table>
						</div>
					</table>
				</s:push>
			</s:form>
		</center>
	</div>
</body>
</html>