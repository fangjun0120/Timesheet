package jfang.project.timesheet.service;

import static org.junit.Assert.assertEquals;
import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.utility.StringProecessUtil;
import jfang.project.timesheet.web.dto.NewProjectDto;
import jfang.project.timesheet.web.dto.UserForm;

import org.dozer.Mapper;
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
public class DozerMappingTest {

    @Autowired
    private Mapper mapper;

    @Test
    public void testMapping() {
        User user = new User("username", "password", Constants.ROLE_EMPLOYEE);
        UserForm form = mapper.map(user, UserForm.class);
        assertEquals("username", form.getUsername());
    }

    @Test
    public void testMappingConvert() {
        NewProjectDto dto = new NewProjectDto();
        dto.setProjectName("name");
        dto.setStartDate("2015/01/01");
        dto.setEndDate("2016/01/01");
        Project project = mapper.map(dto, Project.class);
        assertEquals("name", project.getName());
        assertEquals(115, project.getStartDate().getYear());
        assertEquals(0, project.getStartDate().getMonth());
        assertEquals(1, project.getStartDate().getDate());
    }
}
