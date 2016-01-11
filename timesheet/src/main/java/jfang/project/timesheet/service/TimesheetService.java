package jfang.project.timesheet.service;

import java.util.Date;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.WeekSheet;

public interface TimesheetService {

	WeekSheet getWeekSheetByDate(Date date, Employee employee, String projectName);
}