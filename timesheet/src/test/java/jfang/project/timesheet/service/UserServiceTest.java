package jfang.project.timesheet.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.repository.UserRepository;

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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegisterDuplicateUser() {
        User user = new User("manager", "password", Constants.ROLE_EMPLOYEE);
        long id = userService.registerNewManager(user);
        assertEquals(0, id);
    }

    @Test
    public void testRegisterNewUser() {
        User user = new User("serviceTest", "password", Constants.ROLE_EMPLOYEE);
        long id = userService.registerNewManager(user);
        assertNotEquals(0, id);
    }

    @Test
    public void testResetPassword() {
        User user = userRepository.findByUsername("manager");
        String pswd = userService.resetPasswordFor(user.getUsername());
        user = userRepository.findByUsername("manager");
        assertEquals(pswd, user.getPassword());
        assertNotEquals("manager", user.getPassword());
    }

    @Test
    public void testDisableUser() {
        User user = userRepository.findByUsername("manager");
        userService.disableUser(user.getUsername());
        user = userRepository.findByUsername("manager");
        assertTrue(!user.getEnabled());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("manager", "newpassword", Constants.ROLE_EMPLOYEE);
        user.setFirstname("new fn");
        long id = userService.updateUser(user);
        user = userRepository.findByUserId(id);
        assertEquals("new fn", user.getFirstname());
        assertEquals("newpassword", user.getPassword());
    }
}
