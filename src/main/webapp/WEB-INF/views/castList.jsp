<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

    <json:array>
        <json:property value="Attore Protagonista"/>
        <json:property value="Attrice Protagonista"/>
        <json:property value="Attore non Protagonista"/>
        <json:property value="Attrice non Protagonista"/>
        <json:property value="Comparsa"/>
        <json:property value="Doppiatore"/>
    </json:array>

<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>