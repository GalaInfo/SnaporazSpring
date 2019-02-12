<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

    <json:array>
        <json:property value="Animazione"/>
        <json:property value="Avventura"/>
        <json:property value="Biografico"/>
        <json:property value="Commedia"/>
        <json:property value="Documentario"/>
        <json:property value="Drammatico"/>
        <json:property value="Erotico"/>
        <json:property value="Fantascienza"/>
        <json:property value="Fantasy/Fantastico"/>
        <json:property value="Guerra"/>
        <json:property value="Horror"/>
        <json:property value="Musical"/>
        <json:property value="Storico"/>
        <json:property value="Thriller"/>
        <json:property value="Western"/>
    </json:array>

<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>