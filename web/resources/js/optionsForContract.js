$(document).ready(function () {
    repaintTables();

    function ajaxAddNew(optionId) {
        $.ajax({
            type: "GET",
            url: "addToTariff?childId=" + optionId,
            success: repaintTables()
        });
    }

    function ajaxRemoveExs(optionId) {
        $.ajax({
            type: "GET",
            url: "addToTariff?childId=" + optionId,
            success: repaintTables()
        });
    }

    function repaintTables() {
        $.ajax({
            type: "GET",
            url: "getActualSessionOptions",
            success: function (json) {
                var content = document.getElementById("first_table_body");
                var arr = [];
                if (content.innerHTML != null) {
                    content.innerHTML = "";
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


                }
            },
        });
        $.ajax({
            type: "GET",
            url: "getOtherSessionOptions",
            success: function (json) {
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

})