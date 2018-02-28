<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm"%>

<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>All options</title>
</head>
<body>
<div class="container-fluid">
    <springForm:form name="form1" method="post" action="${contextPath}/options/registerNew"
                     modelAttribute="newOption" class="form-signin">
        <h1>Option's name</h1>
        <%--<spring:bind path="name">--%>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="name" class="form-control"
                        placeholder="Enter option's name" autofocus="true"></form:input>
            <form:errors path="name"></form:errors>
            <form:input type="text" path="price" class="form-control"
                        placeholder="Option's price" autofocus="true"></form:input>
            <form:errors path="price"></form:errors>
            <form:input type="text" path="connectionCost" class="form-control"
                        placeholder="Connection's cost" autofocus="true"></form:input>
            <form:errors path="connectionCost"></form:errors>
        </div>
        <%--</spring:bind>--%>


        <table class="table table-striped">
            <thead>
            <tr>
                <th>Choose compatible options</th>
            </tr>
            <tr>
                <td>Option</td>
                <td>Select</td>
            </tr>
            </thead>
            <tbody id>
            <c:forEach var="option" items="${options}">
                <tr>
                    <td>${option.name}</td>
                    <td>
                        <input type="checkbox" name="optionId" value="${option.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <table>
            <tr>
                <td>
                    <INPUT id="submit" name="submit" type="submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </springForm:form>
</div>
</body>
</html>