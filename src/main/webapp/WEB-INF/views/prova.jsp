<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object name="user">
    <json:property name="id" value="${mail}"/>
</json:object>

<% response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>


