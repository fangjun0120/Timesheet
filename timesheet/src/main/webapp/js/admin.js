var $table = $("#table");
var $remove = $(".table-select");
var selections = [];
var names = [];

$remove.prop('disabled', true);

//enable button when selection is not null
$table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
    $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
    selections = $table.bootstrapTable('getSelections');
});

//fires immediately when the hide instance method has been called
$(".comfirm_win").on('hide.bs.modal', function (event) {
    names = [];
    $(".emp_list").empty();
});

//fires immediately when the show instance method is called
$(".comfirm_win").on('show.bs.modal', function (event) {
    for (var i = 0; i < selections.length; i++) {
        names.push(selections[i].username);
        $(".emp_list").append("<li class='list-group-item'>" + selections[i].username + "</li>");
    }
});

//generate random password
$("#pwd_btn").click(function() {
    var randomstring = Math.random().toString(36).slice(-8);
    $("#new_employee_password").val(randomstring);
});

//ajax call to add a new employee
$("#new_employee_form").submit(function(event) {
    var username = $('#new_employee_username').val();
    var password = $('#new_employee_password').val();
    var json = { "username": username, "password": password};
    $.ajax({
        url: $("#new_employee_form").attr("action"),
        data: JSON.stringify(json),
        type: "POST",

        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "application/json");
        },

        success: function(response) {
            if (response.status == "success") {
                $('#ajax_response').removeClass().addClass("alert alert-success alert-dismissible");
                $('#ajax_response').text(response.message);
                $table.bootstrapTable('refresh');
            }
            else if (response.status == "error") {
                $('#ajax_response').removeClass().addClass("alert alert-danger alert-dismissible");
                $('#ajax_response').text(response.message);
            }
            $('#new_employee_username').val("");
            $('#new_employee_password').val("");
            $('#new_employee').modal("hide");

            $table.bootstrapTable('uncheckAll');
            selections = $table.bootstrapTable('getSelections');
        }
    });

    event.preventDefault();
});

//ajax call to reset password
$("#reset_pwd_btn").click(function(event) {
    $.ajax({
        url: "/timesheet/admin/employee/reset-pwd",
        data: JSON.stringify(names),
        type: "POST",

        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "application/json");
        },

        success: function(response) {
            $("#ajax_response").removeClass().addClass("alert alert-success");
            $("#ajax_response").text(response.message);
            $('#reset_pwd').modal("hide");
            $remove.prop('disabled', true);
        }
    });
    event.preventDefault();
});

//ajax call to lock account
$("#lock_emp_btn").click(function(event) {
    $.ajax({
        url: "/timesheet/admin/employee/lock",
        data: JSON.stringify(names),
        type: "POST",

        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "application/json");
        },

        success: function(response) {
            $("#ajax_response").removeClass().addClass("alert alert-success");
            $("#ajax_response").text(response.message);
            $table.bootstrapTable('refresh');
            $('#lock_employee').modal("hide");
            $remove.prop('disabled', true);
        }
    });
    event.preventDefault();
});