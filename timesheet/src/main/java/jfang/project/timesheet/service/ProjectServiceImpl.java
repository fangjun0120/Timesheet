package jfang.project.timesheet.service;

import javax.annotation.Resource;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.repository.ProjectRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Resource
	private ProjectRepository projectRepository;

	@Resource
	private HumanResourceService humanResourceService;

	@Override
	public List<String> getProjectListByEmployee(Employee employee) {
		if (employee == null) {
			throw new IllegalArgumentException("employee is null.");
		}
		List<Project> projects = projectRepository.findByEmployeeId(employee.getEmployeeId());
		List<String> projectList = new ArrayList<String>();
		for (Project project: projects) {
			projectList.add(project.getName());
			logger.info(project.getName());
		}
		return projectList;
	}

	@Override
	public List<String> getProjectListByManager(Manager manager) {
		if (manager == null) {
			throw new IllegalStateException("manager is null.");
		}
		List<Project> projects = projectRepository.findByManagerId(manager.getManagerId());
		List<String> projectList = new ArrayList<String>();
		for (Project project: projects) {
			projectList.add(project.getName());
		}
		return projectList;
	}

	@Override
	public Project getProjectByName(String name) {
		return projectRepository.findByName(name);
	}

	@Override
	public Project findManagerProject() {
		Manager manager = humanResourceService.getCurrentManager();
		if (manager.getProjects() == null || manager.getProjects().size() == 0) {
			return null;
		}
		return findManagerProject(manager.getProjects().get(0).getName());
	}

	@Override
	public Project findManagerProject(String name) {
		Manager manager = humanResourceService.getCurrentManager();

		for (Project project: manager.getProjects()) {
			if (project.getName().equals(name)) {
				return project;
			}
		}
		throw new IllegalStateException("Project not found with name: " + name);
	}

	@Override
	public Long saveNewProject(Project project) {
		Project tmp = projectRepository.findByName(project.getName());
		if (tmp != null) {
			logger.error("Project already exist");
			return 0l;
		}
		project.setManager(humanResourceService.getCurrentManager());
		tmp = projectRepository.save(project);
		return tmp.getProjectId();
	}

	@Override
	public boolean updateProjectEmployeeList(String projectName, List<String> names) {
		Project project = projectRepository.findByName(projectName);
		Manager manager = project.getManager();
		List<Employee> employeeList = new ArrayList<Employee>();
		for (String name: names) {
			employeeList.add(getEmployeeByName(manager.getEmployees(), name));
		}
		project.setEmployees(employeeList);
		project = projectRepository.save(project);
		logger.debug("Updated project: " + project);
		return true;
	}

	// TODO: make firstname and lastname mandatory
	private Employee getEmployeeByName(List<Employee> employeeList, String fullName) {
		String[] tokens = fullName.split(" ");
		for (Employee employee: employeeList) {
			if (employee.getUser().getFirstname().equals(tokens[0]) &&
					employee.getUser().getLastname().equals(tokens[1])) {
				return employee;
			}
		}
		throw new IllegalStateException("Employee not found with name: " + fullName);
	}
}
