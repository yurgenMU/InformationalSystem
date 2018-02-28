<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags/form"
           prefix="springForm" %>

<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit option</title>
    <%--<script src="/resources/js/lib/jquery-3.3.1.min.js"></script>--%>
    <script type="text/javascript" src="/resources/js/ajaxJson.js"></script>
</head>
<body onload="ajaxGetActual(); ajaxGetRest()">
<%--<script>--%>
<%--$(document).ready(function () {--%>
<%--$('#butt1').click(function () {--%>
<%--//            var par1=$('#inp1').val();--%>
<%--$.ajax({--%>
<%--type:'POST',//тип запроса--%>
<%--data:{option:${option}},//параметры запроса--%>
<%--url:"restOption/${option.id}",//url адрес обработчика--%>
<%--success: funcSuccess(data)//возвращаемый результат от сервера--%>
<%--});--%>
<%--});--%>
<%--});--%>
<%--function funcSuccess(data) {--%>
<%--"asdasdasdasd"--%>
<%--}--%>
<%--</script>--%>
<div class="container">


    <div class="col-sm-7" style="margin:20px 0px 20px 0px">
        <button id="getAllCustomerId" type="button" class="btn btn-primary">Get All Customers</button>

    </div>
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
        <tbody id="first_table_body">

        </tbody>
    </table>
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
        <tbody id="second_table_body">

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
    <script>
        function ajaxGetActual() {
            $.ajax({
                type: "GET",
                url: "getActualOptions/${option.id}",
                success: function (json) {
                    var content = document.getElementById("first_table_body");
                    if(content.innerHTML != null){
                        content.innerHTML = "";
                    }
                    for (var i in json) {
                        var id = json[i].id
                        $("#first_table_body")
                            .append("<tr><td>" + json[i].name + "</td><td>" +
                                "<input type=\"checkbox\" name=\"optionId\" value=" + id + ">" +
                                "</td></tr>");
                    }
                },
            });
        }

        function ajaxGetRest() {
            $.ajax({
                type: "GET",
                url: "getRestOptions/${option.id}",
                success: function (json) {
                    var content = document.getElementById("second_table_body");
                    if(content.innerHTML != null){
                        content.innerHTML = "";
                    }
                    for (var i in json) {
                        var id = json[i].id
                        $("#second_table_body")
                            .append("<tr><td>" + json[i].name + "</td><td>" +
                                "<input type=\"checkbox\" name=\"optionId\" value=" + id + ">" +
                                "</td></tr>");
                    }
                },
            });
        }
    </script>
</div>
</body>
</html>