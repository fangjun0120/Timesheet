package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	Project findByName(String name);
}
