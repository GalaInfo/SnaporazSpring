<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
    <json:array name="mostFoundedProjects" var="project" items="${mostFoundedProjects}">
        <json:object>
            <json:property name="id" value="${project.id}"/>
            <json:property name="title" value="${project.title}"/>
            <json:property name="genres" value="${project.genres}"/>
            <json:property name="min" value="${project.min}"/>
            <json:property name="actual" value="${project.actual}"/>
            <json:property name="image" value="${project.img}"/>
        </json:object>
    </json:array>

    <json:array name="mostRecentProjects" var="project" items="${mostRecentProjects}">
        <json:object>
            <json:property name="id" value="${project.id}"/>
            <json:property name="title" value="${project.title}"/>
            <json:property name="genres" value="${project.genres}"/>
            <json:property name="min" value="${project.min}"/>
            <json:property name="actual" value="${project.actual}"/>
            <json:property name="image" value="${project.img}"/>
        </json:object>
    </json:array>

    <json:array name="closestProjects" var="project" items="${closestProjects}">
        <json:object>
            <json:property name="id" value="${project.id}"/>
            <json:property name="title" value="${project.title}"/>
            <json:property name="genres" value="${project.genres}"/>
            <json:property name="min" value="${project.min}"/>
            <json:property name="actual" value="${project.actual}"/>
            <json:property name="image" value="${project.img}"/>
        </json:object>
    </json:array>
</json:object>

<% response.setContentType("application/json");
response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>
