<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object name="part">
    <json:property name="id" value="${part.id}"/>
    <json:property name="role" value="${part.role}"/>
    <json:property name="character" value="${part.character}"/>
    <json:property name="userId" value="${user.id}"/>
    <json:property name="name" value="${user.name}"/>
    <json:property name="surname" value="${user.surname}"/>
    <json:property name="image" value="${user.image}"/>
    <json:array name="candidacies" var="cand" items="${candidacies}">
        <c:set var="candId" value="cand${cand.id}"/>
        <json:object>
            <json:property name="id" value="${cand.id}"/>
            <json:property name="name" value="${requestScope[candId].name}"/>
            <json:property name="surname" value="${requestScope[candId].surname}"/>
        </json:object>
    </json:array>
</json:object>


<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>