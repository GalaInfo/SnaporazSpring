<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
  <json:array name="projects" var="project" items="${projects}">
    <json:object>
      <json:property name="id" value="${project.id}"/>
      <json:property name="name" value="${project.title}"/>
    </json:object>
  </json:array>
<% response.setContentType("application/json");%>
