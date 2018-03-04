<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/optionsPaint.js"></script>

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
    <springForm:form name="form1" method="post" action="${contextPath}/options/edit/${option.id}"
                     modelAttribute="option" class="form-signin">
        <h2>Edit option</h2>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            Name: <form:input type="text" path="name" class="form-control"
                              placeholder="Edit option's name" autofocus="true"></form:input>
            <form:errors path="name"></form:errors>
            Price: <form:input type="text" path="price" class="form-control"
                               placeholder="Option's price" autofocus="true"></form:input>
            <form:errors path="price"></form:errors>
            Connection cost: <form:input type="text" path="connectionCost" class="form-control"
                                         placeholder="Connection's cost" autofocus="true"></form:input>
            <form:errors path="connectionCost"></form:errors>
        </div>
        <br>
        <input id="saveChanges" type="submit" class="btn btn-primary" value="Save changes"/>
        <a href="${contextPath}/options/remove/${option.id}" class="btn btn-primary" role="button">Delete option</a>
        <div id="t1">
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Compatible options</th>
                </tr>
                <tr>
                    <td>Option</td>
                    <td>Action</td>
                </tr>
                </thead>
                <tbody id="first_table_body">
                </tbody>
            </table>
        </div>
        <br>
        <h3>Add new Options</h3>
        <div id="t2">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Compatible options</th>
                </tr>
                <tr>
                    <td>Option</td>
                    <td>Action</td>
                </tr>
                </thead>
                <tbody id="second_table_body">
                </tbody>
            </table>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <%--<script>--%>
            <%----%>
        <%--</script>--%>
    </springForm:form>
</div>
</body>
</html>