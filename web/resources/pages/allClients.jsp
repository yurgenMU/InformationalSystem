<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
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
        <th><h2>All clients</h2></th>
    </tr>


</div>
<div id="search">
    Search by phone number : <input
        type="text" id="byPhone">
    <button type="button" id="byPhoneButton" class="btn btn-primary">Search</button>
    <br/>
    Search by initials : <input
        type="text" id="byInitials">
    <button type="button" id="byNameButton" class="btn btn-primary">Search</button>
    <br/>
</div>
<div id="description">

</div>

<c:forEach items="${clients}" var="client">
    <button class="accordion">${client.firstName} ${client.lastName}</button>
    <div class="panel">
        First name : <h4> ${client.firstName}</h4><br>
        Last name : <h4>${client.lastName}</h4><br>
        Email : <h4>${client.email}</h4><br>
        Birth : <h4>${client.birth}</h4><br>
        Passport data : <h4> ${client.passportData}</h4><br>
        Address : <h4> ${client.address}</h4><br>
        Contracts : <c:forEach var="contract" items="${client.contracts}">
        <ul>
            <h4>
                <li>${contract.number}</li>
            </h4>
        </ul>
        <a href="${contextPath}/contracts/new" class="btn btn-info" role="button">New Contract</a>
    </c:forEach>
        <a href="${contextPath}/tariffs/edit/${tariff.id}" class="btn btn-primary" role="button">Edit</a>
    </div>
    <%--<td><a href="${contextPath}/OfficeProject/projects/edit?projectId=${mproject.id}">${mproject.name}</a></td>--%>
</c:forEach>
<a href="${contextPath}/restOption" class="btn btn-info" role="button">Add new</a>

<script>

    $(document).ready(function () {
        var name;
        $("#byNameButton").on('click', function () {
            name = $("#byInitials").val();
            getClientData("searchClientByName?name=" + name);
        });
        $("#byPhoneButton").on('click', function () {
            getClientData("searchClientByPhone?name=" + name);
        });


        function getClientData(url) {
            $.ajax({
                type: "GET",
                url: url,
                success: function (json) {
                    var content = document.getElementById("description");
                    var arr = [];
                    if (content.innerHTML != null) {
                        content.innerHTML = "";
                    }
                    for (var i in json) {
                        var numbers;
                        var cont = json[i].contracts;
                        var s = "";
                        for (var j in cont) {
                            s += "<h4>" + cont[j].number + "</h4><br>"
                            console.log(s)
                        }
                        var id = json[i].id;

                        $("#description")
                            .append("First name : <h4>" + json[i].firstName + "</h4><br>" +
                                "        Last name : <h4>" + json[i].lastName + "</h4><br>" +
                                "        Email : <h4>" + json[i].email + "</h4><br>" +
                                "        Birth : <h4>" + json[i].dateValue + "</h4><br>" +
                                "        Passport data : <h4>" + json[i].passportData + "</h4><br>" +
                                "        Address : <h4>" + json[i].address + "</h4><br>" +
                                "Contracts : <h4>" + json[i].contracts + "</h4><br>" +
                                "<a href=\"${contextPath}/clients/edit?clientId=" + id + "\" class=\"btn btn-info\" role=\"button\">Edit</a>"

                        +"<a href=\"${contextPath}/contracts/new?clientId=" + id + "\" class=\"btn btn-info\" role=\"button\">New Contract</a>"
                            );


                    }
                },
            });

        }

    })
</script>
<script src=${contextPath}/resources/js/first.js></script>

</div>
</body>
</html>
