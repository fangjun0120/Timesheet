package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;

public interface HumanResourceService {

	Manager getEmployeeByManagerUsername(String username);

	Long registerNewEmployeeFor(Manager manager, User user);
}