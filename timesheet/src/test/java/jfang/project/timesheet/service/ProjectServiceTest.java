package jfang.project.timesheet.service;

import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.repository.ManagerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by jfang on 1/23/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class,
        DataAccessConfig.class,
        DataSourceConfig.class})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    public void testSaveProject() {
        Manager manager = managerRepository.findByUsername("manager");
        Project project = new Project("testProject", manager, new Date(), new Date());
        long id = projectService.saveNewProject(project);
        assertEquals(3l, id);
    }
}
