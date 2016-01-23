package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("select p from Project p where p.manager.managerId = :managerId")
	List<Project> findByManagerId(@Param("managerId") Long id);

	Project findByName(String name);

	Project save(Project project);
}
