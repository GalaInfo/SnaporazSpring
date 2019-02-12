<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

    <json:array>
        <json:property value="Regista"/>
        <json:property value="Aiuto Regista"/>
        <json:property value="Assistente alla Regia"/>
        <json:property value="Sceneggiatore"/>
        <json:property value="Segretario di Edizione"/>
        <json:property value="Direttore della Fotografia"/>
        <json:property value="Operatore alla Macchina"/>
        <json:property value="Assistente Operatore"/>
        <json:property value="Capo Elettricista"/>
        <json:property value="Elettricista"/>
        <json:property value="Capo Macchinista"/>
        <json:property value="Macchinista"/>
        <json:property value="Costumista"/>
        <json:property value="Capo Truccatore"/>
        <json:property value="Truccatore"/>
        <json:property value="Scenografo"/>
        <json:property value="Fonico"/>
        <json:property value="Microfonista"/>
        <json:property value="Compositore"/>        
        <json:property value="Direttore del Montaggio"/>
        <json:property value="Addetto al Montaggio"/>
    </json:array>

<% response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8383");%>