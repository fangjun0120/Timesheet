package jfang.project.timesheet.service;

import java.util.Date;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.WeekSheet;

public interface TimesheetService {

	WeekSheet getWeekSheetByDate(Date date, Employee employee, Project project);

	WeekSheet getBlankWeekSheet(Date date, Employee employee, Project project);
}