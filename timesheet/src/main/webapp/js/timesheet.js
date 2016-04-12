var dateCurrent;

$('#datepicker').datepicker({
	todayHighlight: true
});

$(document).ready(function() {
    dateCurrent = firstDateOfWeek(new Date());
	var first = $("#project_menu > li > a").first().text();
	$("#project_name").text(first);

	$(".project_item").click(function() {
		$("#project_name").text($(this).text());
		loadWeekSheet();
	});

	loadWeekSheet();
	$('#datepicker').on("changeDate", function() {
		var datePicked = $('#datepicker').datepicker('getDate');
		var firstDate = firstDateOfWeek(datePicked);
		if (dateCurrent != firstDate) {
			dateCurrent = firstDate;
			console.log("Date select: Week sheet starting from " + firstDate);
			loadWeekSheet();
		}
	});
});

//ajax call to refresh sheet
function loadWeekSheet() {
	console.log("ajax loading weeksheet ...");
	var json = { "projectName": $('#project_name').text(), "dateString": dateCurrent};
	$.ajax({
		url: "/timesheet/user/timesheet/date",
		data: JSON.stringify(json),
		type: "POST",

		beforeSend: function(xhrObj) {
			xhrObj.setRequestHeader("Content-Type", "application/json");
			xhrObj.setRequestHeader("Accept", "application/json");
		},

		success: function(response) {
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
			controlInputAndBtn(response.submitted);
		},

		error: function(jqXHR, exception) {
            $(location).attr("href", "/timesheet/ajax-error?status=" + jqXHR.status + "&exception=" + exception);
		}
	});
}

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

// ajax call to update weeksheet
function submitWeekSheet() {
	console.log("ajax submitting weeksheet ...");
	var json = { 
			"startDate": dateCurrent, "projectName": $('#project_name').text(),
			"sunHours": $('#sun_hour').val(),
			"monHours": $('#mon_hour').val(),
			"tueHours": $('#tue_hour').val(),
			"wedHours": $('#wed_hour').val(),
			"thuHours": $('#thu_hour').val(),
			"friHours": $('#fri_hour').val(),
			"satHours": $('#sat_hour').val()
	};

	$.ajax({
		url: "/timesheet/user/timesheet/submit",
		data: JSON.stringify(json),
		type: "POST",

		beforeSend: function(xhrObj) {
			xhrObj.setRequestHeader("Content-Type", "application/json");
			xhrObj.setRequestHeader("Accept", "application/json");
		},

		success: function(response) {
			$("#ajax_response").removeClass().addClass("alert alert-success");
			$("#ajax_response").text(response.message);
			$('#total_hour').text(sumUpHours());
			controlInputAndBtn(true);
		}
	});
}

// ajax call to unsubmit weeksheet
function unsubmitWeekSheet() {
	console.log("ajax unsubmitting weeksheet ...");
	var json = { "projectName": $('#project_name').text(), "dateString": dateCurrent};
	
	$.ajax({
		url: "/timesheet/user/timesheet/unsubmit",
		data: JSON.stringify(json),
		type: "POST",

		beforeSend: function(xhrObj) {
			xhrObj.setRequestHeader("Content-Type", "application/json");
			xhrObj.setRequestHeader("Accept", "application/json");
		},

		success: function(response) {
			$("#ajax_response").removeClass().addClass("alert alert-success");
			$("#ajax_response").text(response.message);
			controlInputAndBtn(false);
		}
	});
}

function sumUpHours() {
	var sum = 0;
	sum += parseInt($('#sun_hour').val());
	sum += parseInt($('#mon_hour').val());
	sum += parseInt($('#tue_hour').val());
	sum += parseInt($('#wed_hour').val());
	sum += parseInt($('#thu_hour').val());
	sum += parseInt($('#fri_hour').val());
	sum += parseInt($('#sat_hour').val());
	return sum;
}

function controlInputAndBtn(submitted) {
	if (submitted) {
		$(".enable-control").prop("disabled", true);
		$("#submit_btn").removeClass().addClass("btn btn-block btn-danger");
		$("#submit_btn").text("Unsubmit");
		$("#submit_btn").unbind().click(function(event) {
			unsubmitWeekSheet();
			event.preventDefault();
		});
	}
	else {
		$(".enable-control").prop("disabled", false);
		$("#submit_btn").removeClass().addClass("btn btn-block btn-primary");
		$("#submit_btn").text("submit");
		$("#submit_btn").unbind().click(function(event) {
			submitWeekSheet();
			event.preventDefault();
		});
	}
}