<%@ page contentType="text/json" %>
<%@ taglib prefix="s" uri="/struts-tags" %>  
{
"ResultSet": {
    "Result":[
    <s:iterator var="s" value="accountNumberList" status="status">  
    {"Text":"<s:property value="%{accountnumber}" />",
    "Value":"<s:property value="%{id}" />"
    }<s:if test="!status.last">,</s:if>
    </s:iterator>       
    ]
  }
}
