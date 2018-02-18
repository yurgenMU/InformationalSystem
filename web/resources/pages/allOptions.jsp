<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<%--<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">--%>
<head>
    <link href="${contextPath}/resources/css/welcome.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Options</title>
</head>
<body>

<div class="container-fluid">
        <tr>
            <th><h2>All options</h2></th>
        </tr>
        <c:forEach items="${options}" var="tariff">
            <button class="accordion" >${tariff.name}</button>
            <div class="panel">
               Price : ${tariff.price}<br>

                <a href="${contextPath}/contracts/showAll">Show all</a><br>
            </div>
                <%--<td><a href="${contextPath}/OfficeProject/projects/edit?projectId=${mproject.id}">${mproject.name}</a></td>--%>
        </c:forEach>
    <a href="${contextPath}/restOption" class="btn btn-info" role="button">Add new</a>

    <script src = ${contextPath}/resources/js/first.js></script>

</div>
</body>
</html>
