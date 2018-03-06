<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <title>Welcome</title>

    <link href="${contextPath}/resources/css/welcome.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <%--Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a><br>--%>
        Welcome ${user.firstName} | <a onclick="document.forms['logoutForm'].submit()">Logout</a><br>

        You logged in as administrator<br>

    </c:if>

</div>
<a href="${contextPath}/OfficeProject/clients/edit?userId=${user.id}">Edit Profile</a><br>

<p><h2>Menu</h2></p>

<button class="accordion">Clients</button>
<div class="panel">
    <a href="${contextPath}/clients/new">Register new</a><br>
    <a href="${contextPath}/clients/management">Management</a><br>

</div>

<button class="accordion">Contracts</button>
<div class="panel">
    <a href="${contextPath}/options/management">Management</a><br>
</div>

<button class="accordion">Options</button>
<div class="panel">
    <a href="${contextPath}/options/registerNew">New option</a><br>
    <a href="${contextPath}/options/showAll">Show all</a><br>
</div>

<button class="accordion">Tariffs</button>
<div class="panel">
    <a href="${contextPath}/tariffs/registerNew">New tariff</a><br>
    <a href="${contextPath}/tariffs/management">Show all</a><br>
</div>

<script src = ${contextPath}/resources/js/first.js></script>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/lib/bootstrap.min.js"></script>
</body>
</html>