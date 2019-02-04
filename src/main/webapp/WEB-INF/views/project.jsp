<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object name="project">
    <json:property name="id" value="${project.id}"/>
    <json:property name="title" value="${project.title}"/>
    <json:property name="genres" value="${project.genres}"/>
    <json:property name="owner" value="${project.owner}"/>
    <json:property name="image" value="${project.img}"/>
    <json:array name="parts" var="part" items="${parts}">
        <json:object>
            <json:property name="id" value="${part.id}"/>
            <json:property name="role" value="${part.role}"/>
            <json:property name="character" value="${part.character}"/>
            <json:property name="user" value="${part.user}"/>
            <json:property name="project" value="${part.project}"/>
        </json:object>
    </json:array>
</json:object>

<% response.setContentType("application/json");%>


