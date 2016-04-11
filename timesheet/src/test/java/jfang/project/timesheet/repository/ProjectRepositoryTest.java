package jfang.project.timesheet.repository;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.model.Project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DataAccessConfig.class,
        DataSourceConfig.class})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class ProjectRepositoryTest {

	@Resource
	private ProjectRepository projectRepository;
	
	@Test
	public void testFindByName() {
		Project project = projectRepository.findByName("bsscores");
		assertEquals(1l, project.getProjectId());
	}

	@Test
	public void testFindByEmployeeId() {
		List<Project> nameList = projectRepository.findByEmployeeId(1l);
		assertEquals(2, nameList.size());
	}

	@Test
	public void testFindByManagerId() {
		List<Project> projects = projectRepository.findByManagerId(1l);
		assertEquals(2, projects.size());
	}
}
