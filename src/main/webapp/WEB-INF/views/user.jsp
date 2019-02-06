<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object name="user">
    <json:property name="id" value="${user.id}"/>
    <json:property name="name" value="${user.name}"/>
    <json:property name="surname" value="${user.surname}"/>
    <json:property name="roles" value="${user.roles}"/>
    <json:property name="image" value="${user.image}"/>
    <json:array name="experiences" var="experience" items="${experiences}">
        <json:object>
            <json:property name="id" value="${experience.id}"/>
            <json:property name="date" value="${experience.start}"/>
            <json:property name="title" value="${experience.title}"/>
            <json:property name="genres" value="${experience.genres}"/>
            <json:property name="role" value="${experience.role}"/>
        </json:object>
    </json:array>
</json:object>

<% response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>


