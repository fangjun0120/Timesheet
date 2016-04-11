package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;

import java.util.List;

public interface ProjectService {

	List<String> getProjectListByEmployee(Employee employee);

	List<String> getProjectListByManager(Manager manager);

	Project getProjectByName(String name);

	Project findManagerProject();

	Project findManagerProject(String name);

	Long saveNewProject(Project project);

	boolean updateProjectEmployeeList(String projectName, List<String> names);
}