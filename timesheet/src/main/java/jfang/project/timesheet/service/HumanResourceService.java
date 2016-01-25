package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;

public interface HumanResourceService {

	Manager getCurrentManager();

	Long registerNewEmployee(User user);
	
	Employee getEmployeeByEmployeeName(String employeeName);
}