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
    <json:property name="prizes" value="${project.prizes}"/>
    <json:property name="actual" value="${project.actual}"/>
    <json:property name="min" value="${project.min}"/>
    <json:property name="days" value="${days}"/>
    <json:property name="donations" value="${project.donations}"/>
    <json:array name="parts" var="part" items="${parts}">
        <json:object>
            <json:property name="id" value="${part.id}"/>
            <json:property name="role" value="${part.role}"/>
            <json:property name="character" value="${part.character}"/>
            <c:set var="userId" value="user${part.user}"/>
            <json:property name="userId" value="${requestScope[userId].id}"/>
            <json:property name="name" value="${requestScope[userId].name}"/>
            <json:property name="surname" value="${requestScope[userId].surname}"/>
            <json:property name="image" value="${requestScope[userId].image}"/>
            <c:set var="partId" value="part${part.id}"/>
            <json:array name="candidates" var="cand" items="${requestScope[partId]}">
                <json:object>
                    <json:property name="id" value="${cand.id}"/>
                    <json:property name="name" value="${cand.name}"/>
                    <json:property name="surname" value="${cand.surname}"/>
                </json:object>
            </json:array>
        </json:object>
    </json:array>
    <json:array name="related" var="rel" items="${related}">
        <json:object>
            <json:property name="id" value="${rel.id}"/>
            <json:property name="title" value="${rel.title}"/>
            <json:property name="genres" value="${rel.genres}"/>
            <json:property name="image" value="${rel.img}"/>
            <json:property name="actual" value="${rel.actual}"/>
            <json:property name="min" value="${rel.min}"/>
        </json:object>
    </json:array>
</json:object>

<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>