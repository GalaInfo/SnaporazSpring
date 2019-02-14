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
    <json:property name="role" value="${user.role}"/>
    <json:property name="image" value="${user.image}"/>
    <json:property name="birth" value="${user.birth}"/>
    <json:property name="nation" value="${user.nation}"/>
    <json:property name="mail" value="${user.mail}"/>
    <json:array name="experiences" var="experience" items="${experiences}">
        <json:object>
            <json:property name="id" value="${experience.id}"/>
            <json:property name="date" value="${experience.start}"/>
            <json:property name="title" value="${experience.title}"/>
            <json:property name="genres" value="${experience.genres}"/>
            <json:property name="role" value="${experience.role}"/>
            <json:property name="character" value="${experience.character}"/>
        </json:object>
    </json:array>
</json:object>

<% response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>


