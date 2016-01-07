$('#datepicker').datepicker();

// save the embedded datepicker value to a hidden field
$('#datepicker').on("changeDate", function() {
    $('#my_hidden_input').val(
        $('#datepicker').datepicker('getFormattedDate')
    );
    log.console($('#datepicker').datepicker('getFormattedDate'));
});

