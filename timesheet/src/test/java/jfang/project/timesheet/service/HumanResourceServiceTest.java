package jfang.project.timesheet.service;

import static org.junit.Assert.assertEquals;

import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jfang on 4/13/2016.
 */
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
    public void testGetEmployeeByRealName() {
        Employee employee = humanResourceService.getEmployeeByRealName("Mike Young");
        assertEquals(1l, employee.getEmployeeId());
    }
}
