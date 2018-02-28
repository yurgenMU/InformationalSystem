$(document).ready(function () {
    // updateSessionVariables();
    // // GET REQUEST
    // $("#getAllCustomerId").click(function (event) {
    //     event.preventDefault();
    //     ajaxGetActual();
    //     ajaxGetRest();
    // });

    // DO GET
    function  f() {

        var cell = getElementInsideContainer("second_table_body", "delete2")

        cell.click(function () {
            alert("hello");
        })
    }

    function ajaxAddNew(targetId, optionId) {
        $.ajax({
            type: "GET",
            url: "addOtherOption?parentId=" + targetId + "&childId=" + optionId,
            success: updateSessionVariables()
        });
    }

    function ajaxRemoveExs(targetId, optionId) {
        $.ajax({
            type: "GET",
            url: "removeActualOption?parentId=" + targetId + "&childId=" + optionId,
            success: updateSessionVariables()
        });
    }

    function updateSessionVariables() {
        $.ajax({
            type: "GET",
            url: "getActualSessionOptions",
            success: function (json) {
                var content = document.getElementById("first_table_body");
                if (content.innerHTML != null) {
                    content.innerHTML = "";
                }
                for (var i in json) {
                    var id = json[i].id
                    var name = "add" + id
                    $("#first_table_body")
                        .append("<tr><td>" + json[i].name + "</td><td>" +
                            "<button id=" + name + " type=\"button\" class=\"btn btn-primary\">Remove</button>" +
                            "</td></tr>");
                    var a = "#" + name;
                    var b = $(a);
                    b.click(function (event) {
                        ajaxRemoveExs(id)
                    })
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
                    $("#second_table_body")
                        .append("<tr><td>" + json[i].name + "</td><td>" +
                            "<button id=" + name + " type=\"button\" class=\"btn btn-primary\">Add</button>" +
                            "</td></tr>");
                    var b = document.getElementById(name)
                    var a = "#" + name;
                    var b = $(a);
                    // b.click(function (event) {
                    //     event.preventDefault();
                    //     ajaxRemoveExs(id);
                    //     console.log("asdasdasd")
                    //
                    // })

                }
            },
        });
    }
})