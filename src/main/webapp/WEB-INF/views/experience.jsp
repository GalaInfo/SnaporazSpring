<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object name="experience">
    <json:property name="id" value="${experience.id}"/>
    <json:property name="title" value="${experience.title}"/>
    <json:property name="genres" value="${experience.genres}"/>
    <json:property name="role" value="${experience.role}"/>
    <json:property name="start" value="${experience.start}"/>
    <json:property name="end" value="${experience.end}"/>
</json:object>


<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>