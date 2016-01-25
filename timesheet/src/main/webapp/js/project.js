$(document).ready(function() {
    $('#multiselect').multiselect();

    $('#f_start_date').datepicker({
        format: "yyyy/mm/dd"
    });
    $('#f_end_date').datepicker({
        format: "yyyy/mm/dd"
    });

    loadProjectList();

    $('#new_proj_submit').click(function(event) {
        var json = {'projectName': $('#f_proj_name').val(),
            'startDate': $('#f_start_date').val(), 'endDate': $('#f_end_date').val()};
        console.log(json);
        $.ajax({
            url: "/timesheet/admin/project/new",
            data: JSON.stringify(json),
            type: "POST",

            beforeSend: function(xhrObj){
                xhrObj.setRequestHeader("Content-Type", "application/json");
                xhrObj.setRequestHeader("Accept", "application/json");
            },

            success: function(response) {
                $("#ajax_response").text(response.message);
                if (response.status === "success") {
                    $("#ajax_response").removeClass().addClass("alert alert-success");
                }
                else {
                    $("#ajax_response").removeClass().addClass("alert alert-danger");
                }
                loadProjectList();
                $("#new_proj").modal("hide");
            }
        });
        event.preventDefault();
    });
});

//ajax call to udpate employee list
$("#submit_btn").click(function(event) {
    var nameList = [];
    $('#multiselect_to').find('option').each(function() {
        nameList.push($(this).val());
    });
    var json = {'projectName': $('#project_title').text().split('(')[0],
        'employeeNameList': nameList};
    console.log(json);

    $.ajax({
        url: "/timesheet/admin/project/update",
        data: JSON.stringify(json),
        type: "POST",

        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "application/json");
        },

        success: function(response) {
            $("#ajax_response").removeClass().addClass("alert alert-success");
            $("#ajax_response").text(response.message);
        }
    });
    event.preventDefault();
});

function loadProjectList() {
    console.log("ajax loading project list ...");
    $('#project_list').empty();
    $.ajax({
        url: "/timesheet/admin/project/list",
        type: "GET",

        beforeSend: function(xhrObj) {
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "application/json");
        },

        success: function(response) {
            for (var i = 0; i < response.length; i++) {
                $('#project_list').append('<li><a href="/timesheet/admin/project/'
                    + response[i] + '">' + response[i] + '</a></li>');
            }
        }
    });
}
