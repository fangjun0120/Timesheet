package jfang.project.timesheet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.constant.ResponseStatus;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.service.HumanResourceService;
import jfang.project.timesheet.service.UserService;
import jfang.project.timesheet.web.dto.AjaxResponseStatus;
import jfang.project.timesheet.web.dto.EmployeeResponseDto;
import jfang.project.timesheet.web.dto.NewEmployeeDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("admin")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private HumanResourceService humanResourceService;
	
    @RequestMapping("/employee")
    public String getEmployees() {
    	return "admin/users";
    }
    
    @ResponseBody
    @RequestMapping(value = "/employee/data", produces = "application/json")
    public List<EmployeeResponseDto> getEmployeeData() {
        List<EmployeeResponseDto> list = new ArrayList<EmployeeResponseDto>();
        Manager manager = getCurrentManager();
        for (Employee employee: manager.getEmployees()) {
        	list.add(mapUserToEmployee(employee.getUser()));
        }
    	return list;
    }
    
    @ResponseBody
    @RequestMapping(value="/employee/new", method=RequestMethod.POST)
    public AjaxResponseStatus addNewEmployee(@RequestBody NewEmployeeDto newEmployeeDto) {
    	String username = newEmployeeDto.getUsername();
    	String password = newEmployeeDto.getPassword();
    	User user = new User(username, password, Constants.ROLE_EMPLOYEE);
    	Manager manager = getCurrentManager();
    	Long id = humanResourceService.registerNewEmployeeFor(manager, user);
    	
    	AjaxResponseStatus response = new AjaxResponseStatus();
    	// username already exists
    	if (id == 0) {
    		response.setStatus(ResponseStatus.ERROR.value());
    		response.setMessage("The user name is already used.");
    	}
    	else {
    		response.setStatus(ResponseStatus.SUCCESS.value());
    		response.setMessage("User created successfully. Please write down the credentials: " + username + " / " + password);
    		logger.info(String.format("New employee added by %s: %s / %s", 
    				manager.getUser().getUsername(), user.getUsername(), user.getPassword()));
    	}
    	
    	return response;
    }
    
    @ResponseBody
    @RequestMapping(value="/employee/reset-pwd", method=RequestMethod.POST)
    public AjaxResponseStatus resetPassword(@RequestBody List<String> usernames) {
    	StringBuilder buffer = new StringBuilder();
    	AjaxResponseStatus response = new AjaxResponseStatus();
    	for (String username: usernames) {
    		String pwd = userService.resetPasswordFor(username);
    		buffer.append(username + " / " + pwd + "\n");
    	}
    	response.setStatus(ResponseStatus.SUCCESS.value());
    	response.setMessage("Password reset for: \n" + buffer.toString());
    	return response;
    }
    
    @ResponseBody
    @RequestMapping(value="/employee/lock", method=RequestMethod.POST)
    public AjaxResponseStatus disableUser(@RequestBody List<String> usernames) {
    	StringBuilder buffer = new StringBuilder();
    	AjaxResponseStatus response = new AjaxResponseStatus();
    	for (String username: usernames) {
    		userService.disableUser(username);
    		buffer.append(username + "\n");
    	}
    	response.setStatus(ResponseStatus.SUCCESS.value());
    	response.setMessage("User locked for: \n" + buffer.toString());
    	return response;
    }
    
    private Manager getCurrentManager() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
    	return humanResourceService.getEmployeeByManagerUsername(name);
    }
    
    private EmployeeResponseDto mapUserToEmployee(User user) {
    	EmployeeResponseDto dto = new EmployeeResponseDto();
    	dto.setEmail(user.getEmail());
    	dto.setEnabled(user.getEnabled()?"Y":"N");
    	dto.setFirstname(user.getFirstname());
    	dto.setLastname(user.getLastname());
    	dto.setUsername(user.getUsername());
    	return dto;
    }
}
