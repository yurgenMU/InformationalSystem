<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Register new client</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-datetimepicker.min.css"/>
    <script src="${contextPath}/resources/js/lib/jquery-3.3.1.min.js"></script>


</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="client" class="form-signin">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h2 class="form-signin-heading">Register new client</h2>
        <spring:bind path="firstName">

            First name :
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="firstName" class="form-control"
                            autofocus="true"></form:input>
                <form:errors path="firstName"></form:errors>
            </div>
        </spring:bind>
        Last name :
        <spring:bind path="lastName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="lastName" class="form-control"
                ></form:input>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>

        Birth :
        <spring:bind path="birth">
            <div class="form-group ${status.error ? 'has-error' : ''}" style="position: relative">
                <form:input type="text" id="datepicker" path="dateValue" class="form-control"
                ></form:input>
                <form:errors path="dateValue"></form:errors>
            </div>

        </spring:bind>

        Passport data :
        <spring:bind path="passportData">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea type="text" path="passportData" class="form-control"
                ></form:textarea>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>

        Address :
        <spring:bind path="address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea type="text" path="address" class="form-control"
                ></form:textarea>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>


        Email :
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class="form-control"
                            placeholder="Enter your email"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>


        Pasword :
        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        Confirm password
        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="confirmPassword"></form:errors>
            </div>
        </spring:bind>


        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>

<script src="/resources/js/moment-with-locales.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#datepicker').datetimepicker({
            // locale: 'ru',
            stepping: 10,
            format: 'DD.MM.YYYY',
            daysOfWeekDisabled: [0, 6]
        });
        $('#datepickerasd').datetimepicker({
            locale: 'ru'
        });
    });
</script>

<!-- /container -->
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>--%>
</body>
</html>