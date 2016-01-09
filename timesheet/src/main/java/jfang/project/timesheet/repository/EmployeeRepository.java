package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@SuppressWarnings("unchecked")
	Employee save(Employee employee);
	
	@Query("select e from Employee e where e.user.username = :username")
	Employee findByUsername(@Param("username") String username);
}
