package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@SuppressWarnings("unchecked")
	Employee save(Employee employee);
}
