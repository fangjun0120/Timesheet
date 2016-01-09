package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Project;

public interface ProjectService {

	/**
	 * The project should exists, since the name is got from query.
	 *  
	 * @param name
	 * @return
	 */
	 Project getProjectByName(String name);

}