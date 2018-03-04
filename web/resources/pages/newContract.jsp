<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>

<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
<%--<script type="text/javascript" src="/resources/js/optionsForTariff.js"></script>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>New contract</title>
</head>

<body>
<div class="container-fluid">
    <div>
        <h1>${client.firstName} ${client.lastName}</h1><br>
    </div>

    <div id="phone">
        <input type="text" id="phoneNumber">
        <button type="button" id="number" class="btn btn-primary">Generate phone number</button>
    </div>


    <div id="customerBasket">
        asdasd
    </div>
    <style type="text/css">
        #customerBasket {
            position: absolute;
            top: 0;
            right: 0;
        }
    </style>


    <style type="text/css">
        #tariffSelector {
            position: absolute;
            top: 90px;
            left: 10px;
        }
    </style>

    <select name="tariff" id="tariffSelector">
        <c:forEach items="${tariffs}" var="tariff">
            <option value="${tariff.id}">${tariff.name}</option>
        </c:forEach>
    </select>
    <br>
    <div id="t1">
        <br>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Chosen options</th>
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
                <th>Available options</th>
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
    <%--</c:forEach>--%>
</div>
</body>
</html>
