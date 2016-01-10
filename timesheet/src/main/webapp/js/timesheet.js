var dateCurrent = firstDateOfWeek(new Date());

$('#datepicker').datepicker({
	todayHighlight: true
});

// save the embedded datepicker value to a hidden field
$('#datepicker').on("changeDate", function() {
    var datePicked = $('#datepicker').datepicker('getDate');
    var firstDate = firstDateOfWeek(datePicked);
    if (dateCurrent != firstDate) {
    	dateCurrent = firstDate;
    	console.log("Week sheet starting from " + firstDate);
    	var json = { "projectName": $('#project_name').val(), "dateString": dateCurrent};
    	
    	// ajax call to refresh sheet
    	$.ajax({
    		url: "/timesheet/user/timesheet/date",
    		data: JSON.stringify(json),
    		type: "POST",

    		beforeSend: function(xhrObj){
    			xhrObj.setRequestHeader("Content-Type", "application/json");
    			xhrObj.setRequestHeader("Accept", "application/json");
    		},

    		success: function(response) {
    			console.log(response);
    			$('#sun_date').text(response.sunDate);
    			$('#sun_hour').val(response.sunHours);
    			$('#mon_date').text(response.monDate);
    			$('#mon_hour').val(response.monHours);
    			$('#tue_date').text(response.tueDate);
    			$('#tue_hour').val(response.tueHours);
    			$('#wed_date').text(response.wedDate);
    			$('#wed_hour').val(response.wedHours);
    			$('#thu_date').text(response.thuDate);
    			$('#thu_hour').val(response.thuHours);
    			$('#fri_date').text(response.friDate);
    			$('#fri_hour').val(response.friHours);
    			$('#sat_date').text(response.satDate);
    			$('#sat_hour').val(response.satHours);
    			$('#total_hour').text(response.totalHours);
    			
    		}
    	});
    }
});

function dateToString(date) {
	var year = date.getFullYear();
	var mon = date.getMonth() + 1; // zero-based
	var day = date.getDate();
	return year + "/" + mon + "/" + day;
}

function firstDateOfWeek(date) {
	var dateInt = date.getDay();
	date.setDate(date.getDate() - dateInt);
	return dateToString(date);
}