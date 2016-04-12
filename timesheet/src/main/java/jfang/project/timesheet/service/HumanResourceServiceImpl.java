package jfang.project.timesheet.service;

import javax.annotation.Resource;

import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.repository.EmployeeRepository;
import jfang.project.timesheet.repository.ManagerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    public Manager getCurrentManager() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return managerRepository.findByUsername(name);
    }

    // TODO: validate user, should not accept empty username
    @Override
    public Long registerNewEmployee(User user) {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        Employee employee = new Employee();
        employee.setManager(getCurrentManager());
        employee.setUser(user);
        try {
            employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            logger.error("Username exists.", e.getMessage());
            return 0l;
        }
        return user.getUserId();
    }

    @Override
    @Cacheable(value = "employeeCache")
    public Employee getEmployeeByUsername(String employeeName) {
        return employeeRepository.findByUsername(employeeName);
    }

    @Override
    public Employee getEmployeeByRealName(String name) {
        String[] tokens = name.split(" ");
        return employeeRepository.findByFirstAndLastName(tokens[0], tokens[1]);
    }

    @Override
    public List<String> getEmployeeNameListByProjectName(String projectName) {
        List<Employee> employeeList = employeeRepository.findEmployeeListByProject(projectName);
        List<String> nameList = new ArrayList<String>();
        for (Employee employee: employeeList) {
            nameList.add(employee.getUser().getFirstname() + " " + employee.getUser().getLastname());
        }
        return nameList;
    }
}
