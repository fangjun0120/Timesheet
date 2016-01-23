package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;

import java.util.List;

public interface ProjectService {

	List<String> getProjectListByManager(Manager manager);

	Project getProjectByName(String name);

	Long saveNewProject(Project project);
}