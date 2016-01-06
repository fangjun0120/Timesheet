package jfang.project.timesheet.service;

import javax.annotation.Resource;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.repository.EmployeeRepository;
import jfang.project.timesheet.repository.ManagerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;




@Service
public class HumanResourceServiceImpl implements HumanResourceService {

	private final Logger logger = LoggerFactory.getLogger(HumanResourceServiceImpl.class);
	
	@Resource
	private ManagerRepository managerRepository;
	
	@Resource
	private EmployeeRepository employeeRepository;

	/**
	 * throw IllegalStateException when the Manager not found since the username is given by spring security
	 */
	@Override
	public Manager getEmployeeByManagerUsername(String username) {
		return managerRepository.findByUsername(username);
	}
	
	@Override
	public Long registerNewEmployeeFor(Manager manager, User user) {
		if (user == null)
            throw new IllegalArgumentException("user is null");
        Employee employee = new Employee();
        employee.setManager(manager);
        employee.setUser(user);
        try {
			employee = employeeRepository.save(employee);
		} catch (DataIntegrityViolationException e) {
			logger.error("Username exists.", e.getMessage());
			return 0l;
		}
        return user.getUserId();
	}
}
