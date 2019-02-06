<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
            <json:property name="success" value="${success}"/>
            <json:property name="response" value="${response}"/>
</json:object>

<% response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>
