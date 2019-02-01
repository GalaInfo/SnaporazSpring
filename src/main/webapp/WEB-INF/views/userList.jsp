<%-- 
    Document   : userList
    Created on : 29-gen-2019, 17.19.55
    Author     : Neno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
  <json:array name="users" var="user" items="${users}">
    <json:object>
      <json:property name="id" value="${user.id}"/>
      <json:property name="name" value="${user.name}"/>
      <json:property name="surname" value="${user.surname}"/>
      <json:property name="roles" value="${user.roles}"/>
      <json:property name="image" value="${user.image}"/>
    </json:object>
  </json:array>
<% response.setContentType("application/json");%>


