package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;

import java.util.List;

public interface HumanResourceService {

    Manager getCurrentManager();

    Long registerNewEmployee(User user);

    Employee getEmployeeByUsername(String employeeName);

    Employee getEmployeeByRealName(String name);

    List<String> getEmployeeNameListByProjectName(String projectName);
}