package jfang.project.timesheet.service;

import javax.annotation.Resource;

import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.repository.ProjectRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Resource
	private ProjectRepository projectRepository;

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
	public Long saveNewProject(Project project) {
		project.setEmployees(project.getManager().getEmployees());
		Project tmp = projectRepository.findByName(project.getName());
		if (tmp != null) {
			logger.error("Project already exist");
			return 0l;
		}
		tmp = projectRepository.save(project);
		return tmp.getProjectId();
	}
}
