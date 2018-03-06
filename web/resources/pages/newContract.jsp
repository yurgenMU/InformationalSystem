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
        <script>
            $(document).ready(function () {
                $("#number").on('click', function () {
                    $.ajax({
                        type: "GET",
                        url: "generatePhoneNumber",
                        success: function (json) {
                            $("#phoneNumber").val(json);
                            $("#basketPhone").text(json);
                            // setBasketPhone(json)
                        }
                    });
                });
            })
        </script>
    </div>


    <div id="customerBasket">
        <h4>Customer's basket</h4><br>
        <a href="javascript:PopUpShow()">Show selected content</a>
    </div>
    <div class="b-popup" id="popup1">
        <springForm:form name="form1" method="post" action="${contextPath}/contracts/new"
                         class="form-signin">
            <div class="b-popup-content">
                Phone number:<h4><span id="basketPhone"></span></h4><br>
                Tariff: <h4><span id="basketTariff"></span></h4><br>
                Chosen options :
                <div id="basketOptions"></div>
                <br>
                <a href="javascript:PopUpHide()">Hide</a>
                <a href="/contracts/saveNew?clientId=${client.id}">Save</a>
            </div>
        </springForm:form>
    </div>
    <style>

        /*#customerBasket{*/
        /*width:300px;*/
        /*height:150px;*/
        /*background-color: #ccc;*/
        /*margin:0px auto;*/
        /*padding:10px;*/
        /*font-size:30px;*/
        /*color: #fff;*/
        /*}*/
        .b-popup {
            width: 100%;
            min-height: 100%;
            background-color: rgba(0, 0, 0, 0.01);
            overflow: hidden;
            position: fixed;
            top: 0px;
        }

        .b-popup .b-popup-content {
            margin: 40px auto 0px auto;
            width: 800px;
            height: 400px;
            padding: 10px;
            background-color: #f5f5f5;
            border-radius: 5px;
            box-shadow: 0px 0px 10px #000;
        }
    </style>

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
            top: 130px;
            left: 10px;
        }
    </style>


    <select name="tariff" id="tariffSelector">
        <option value=""></option>
        <c:forEach items="${tariffs}" var="tariff">
            <option value="${tariff.id}" onclick="getSelectedTariff()">${tariff.name}</option>
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
</div>
</body>
<script>
    $(document).ready(function () {
        PopUpHide();
        getSelectedTariff();
    });

    function PopUpShow() {
        $("#popup1").show();
    }

    function PopUpHide() {
        $("#popup1").hide();
    }

    function getSelectedTariff() {
        $("#tariffSelector").change(function () {
            var cur_value = $('option:selected', this).text();

            $("#basketTariff").text(cur_value);
            setBasketTariff(cur_value);
            return cur_value;
        });
    }

    function setBasketTariff(value) {
        $.ajax({
            type: "GET",
            url: "addTariffToBasket?tariff=" + value,
            success: function (ans) {
                if (ans == 'OK') {
                    repaintTables();
                }
            }
        });
    }

    function setBasketPhone(value) {
        $.ajax({
            type: "GET",
            url: "addPhoneToBasket?phone=" + value,
        });
    }


    function repaintTables() {
        $.ajax({
            type: "GET",
            url: "getChosenOption",
            success: function (json) {
                console.log(json);
                var content = document.getElementById("first_table_body");
                var arr = [];
                if (content.innerHTML != null) {
                    content.innerHTML = "";
                }
                var basketContent = document.getElementById("basketOptions");
                var arr = [];
                if (basketContent.innerHTML != null) {
                    basketContent.innerHTML = "";
                }
                for (var i in json) {
                    var id = json[i].id
                    var name = "delete" + id
                    var table = $("#first_table_body");
                    n = $("<tr data-id=" + id + "><td>" + json[i].name + "</td><td>" +
                        "<button type=\"button\" class=\"btn btn-primary\">Remove</button>" +
                        "</td></tr>")
                        .appendTo(table)
                        .on('click', function () {
                            var optId = $(this).attr("data-id");
                            ajaxRemoveExs(optId);
                        });
                    $("#basketOptions").append("<h4>" + json[i].name + "</h4><br>");
                    // t = $(json[i].name)
                    //     .appendTo(subTable);

                }
            },
        });
        $.ajax({
            type: "GET",
            url: "getAvailableOptions",
            success: function (json) {
                console.log(json);
                var content = document.getElementById("second_table_body");
                if (content.innerHTML != null) {
                    content.innerHTML = "";
                }
                for (var i in json) {
                    var id = json[i].id
                    var name = "delete" + id
                    var table = $("#second_table_body");

                    d = $("<tr data-id=" + id + "><td>" + json[i].name + "</td><td>" +
                        "<button type=\"button\" class=\"btn btn-primary\">Add</button>" +
                        "</td></tr>")
                        .appendTo(table)
                        .on('click', function () {
                            var optId = $(this).attr("data-id");
                            ajaxAddNew(optId);
                        });


                }
            },
        });
    }


    function ajaxAddNew(optionId) {
        $.ajax({
            type: "GET",
            url: "addToContract?childId=" + optionId,
            success: repaintTables()
        });
    }

    function ajaxRemoveExs(optionId) {
        $.ajax({
            type: "GET",
            url: "removeFromContract?childId=" + optionId,
            success: repaintTables()
        });
    }
</script>
</html>
