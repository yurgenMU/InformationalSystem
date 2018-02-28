$(document).ready(function () {
    $('#userName').blur(function () {
        $.ajax({
            url: 'GetUserServlet',
            data: {
                options: $('#options').val()
            },
            success: function (responseText) {
                $('#ajaxGetUserServletResponse').text(responseText);
            }
        });
    });
});