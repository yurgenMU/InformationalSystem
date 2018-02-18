<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>

<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit project</title>
</head>
<body>
<div class="container-fluid">



    <a href="/" class="btn btn-info" role="button">To main page</a>
    <a href="projects/remove?projectId=${mproject.id}" class="btn btn-info" role="button">Remove project</a>


    <springForm:form name="form1" method="post" action="/OfficeProject/projects/changeName?projectId=${mproject.id}"
                     modelAttribute="mproject"
                     class="form-signin">

        <h1>Project's name</h1>
        <h2>
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control"
                            value="${mproject.name}" autofocus="true"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </h2>
        <input id="change name" name="submit" type="submit" value="Change"/>
    </springForm:form>


    <form name="form2" method="post" action="/OfficeProject/OfficeProject/projects/removeFrom?projectId=${mproject.id}"
          modelAttribute="mproject" class="form-signin">

        <table class="table table-striped">
            <thead>
            <tr>
                <th>Users working in the project</th>
            </tr>
            <tr>
                <td>User</td>
                <td>Select</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${actualUsers}">
                <tr>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>
                        <input type="checkbox" name="auserId" value="${user.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <table>
            <tr>
                <td>
                    <INPUT id="remove" name="submit" type="submit" value="Remove selected"/>
                </td>
            </tr>
        </table>
    </form>


    <form name="form3" method="post" action="/OfficeProject/OfficeProject/projects/addInto?projectId=${mproject.id}"
          modelAttribute="mproject" class="form-signin">
        <h1>Add users into project</h1>
        <%--<spring:bind path="name">--%>

        <%--</spring:bind>--%>


        <table class="table table-striped">
            <thead>
            <tr>
                <th>Choose users</th>
            </tr>
            <tr>
                <td>User</td>
                <td>Select</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="nuser" items="${nUsers}">
                <tr>
                    <td>${nuser.firstName} ${nuser.lastName}</td>
                    <td>
                        <input type="checkbox" name="nuserId" value="${nuser.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <table>
            <tr>
                <td>
                    <INPUT id="submit" name="submit" type="submit" value="Add selected"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>