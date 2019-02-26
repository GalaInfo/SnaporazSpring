<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

    <json:object>
        <json:property name="UserName" value="${user.name}"/>
        <json:property name="UserSurname" value="${user.surname}"/>
        <json:property name="UserId" value="${user.id}"/>
    </json:object>

<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>