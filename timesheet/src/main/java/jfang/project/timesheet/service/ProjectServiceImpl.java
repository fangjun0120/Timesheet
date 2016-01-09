package jfang.project.timesheet.service;

import javax.annotation.Resource;

import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.repository.ProjectRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Resource
	private ProjectRepository projectRepository;

	@Override
	public Project getProjectByName(String name) {
		return projectRepository.findByName(name);
	}
}
