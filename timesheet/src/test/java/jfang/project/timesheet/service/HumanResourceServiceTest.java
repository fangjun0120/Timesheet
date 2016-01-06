package jfang.project.timesheet.service;

import static org.junit.Assert.*;
import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		ServiceConfig.class,
		DataAccessConfig.class,
		DataSourceConfig.class})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class HumanResourceServiceTest {

	@Autowired
	private HumanResourceService humanResourceService;
	
	@Test
	public void testRegisterNewEmployeeFor() {
		Manager manager = humanResourceService.getEmployeeByManagerUsername("manager");
		User user = new User("newEmployee", "password", Constants.ROLE_EMPLOYEE);
		long id = humanResourceService.registerNewEmployeeFor(manager, user);
		assertNotEquals(3, id);
		manager = humanResourceService.getEmployeeByManagerUsername("manager");
		assertEquals(3, manager.getEmployees().size());
	}
}
