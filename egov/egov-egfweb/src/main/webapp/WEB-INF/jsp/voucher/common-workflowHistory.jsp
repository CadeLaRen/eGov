<%@ include file="/includes/taglibs.jsp"%>
<%@ page language="java"%>
<span>

	<div id="wfHistoryDiv">
		<h2>Workflow History</h2>
		<c:import url="/WEB-INF/jsp/workflow/workflowHistory.jsp"
			context="/egi">
			<c:param name="stateId" value="${stateId}"></c:param>
		</c:import>
	</div>
</span>
<br />
<br />
<input type="button" id="button2" value="Close"
	onclick="javascript:window.close()" class="button" />

