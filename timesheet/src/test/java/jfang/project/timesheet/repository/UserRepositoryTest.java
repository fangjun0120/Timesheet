package jfang.project.timesheet.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DataAccessConfig.class,
        DataSourceConfig.class})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindById() {
		User user = userRepository.findByUserId(1l);
		assertEquals("manager", user.getUsername());
	}
	
	@Test
	public void testFindByUsername() {
		User user = userRepository.findByUsername("manager");
		assertEquals(Long.valueOf(1l), user.getUserId());
		user = userRepository.findByUsername("xxx");
		assertNull(user);
	}
	
	@Test
	public void testInsertDuplicateUser() {
		User user = new User("manager", "password", Constants.ROLE_EMPLOYEE);
		boolean raised = false;
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			raised = true;
		}
		assertTrue(raised);
	}
	
	@Test
	public void testInsertNewUser() {
		User user = new User("newUser", "password", Constants.ROLE_EMPLOYEE);
		user = userRepository.save(user);
		assertNotNull(user.getUserId());
	}
	
	@Test
	public void testUpdateUser() {
		User user = userRepository.findByUsername("manager");
		user.setFirstname("new first name");
		user = userRepository.save(user);
		assertEquals("new first name", user.getFirstname());
	}
}
