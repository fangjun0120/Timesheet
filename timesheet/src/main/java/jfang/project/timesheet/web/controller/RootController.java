package jfang.project.timesheet.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.service.UserService;
import jfang.project.timesheet.web.dto.UserForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Resource
	private UserService userService;
	
	@Value("${userform.pwdconfirm.notmatch}")
	public String pwdConfirmErrorMessage;
	
	@Value("${userform.username.conflict}")
	public String usernameConflict;
	
	@Value("${userform.organization.conflict}")
	public String orgNameConflict;
	
	/** Home page. */
    @RequestMapping("/")
    public String index() {
    	SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            logger.debug("login as " + auth.getAuthority());
        }
        return "index";
    }

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String getRegistrationForm(Model model) {
    	model.addAttribute("userForm", new UserForm());
    	return "register";
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String registrationPost(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
    	logger.debug("user registered: " + userForm);
    	
    	// field validation fails
    	if (result.hasErrors())
    		return "register";
    	
    	// passwords not match
    	if (!userForm.getPassword().equals(userForm.getPwdConfirm())) {
    		logger.debug("password not match.");
    		result.addError(new FieldError("usreForm", "pwdConfirm", pwdConfirmErrorMessage));
    		return "register";
    	}
    	
    	Long id = userService.registerNewManager(mapUserFormToUser(userForm, Constants.ROLE_MANAGER));
    	// username already exists
    	if (id == 0) {
    		logger.debug("Username already exists.");
    		result.addError(new FieldError("usreForm", "username", usernameConflict));
    		return "register";
    	}
    	
    	return "login";
    }

    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String getProfileUpdateForm(Model model) {
    	SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
    	model.addAttribute("userForm", mapUserToUserForm(user));
    	return "user/profile";
    }
    
    @RequestMapping(value="/profile", method=RequestMethod.POST)
    public String udpateProfile(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
    	// field validation fails
    	if (result.hasErrors())
    		return "user/profile";
    	
    	// passwords not match
    	if (!userForm.getPassword().equals(userForm.getPwdConfirm())) {
    		logger.debug("password not match.");
    		result.addError(new FieldError("usreForm", "pwdConfirm", pwdConfirmErrorMessage));
    		return "user/profile";
    	}
    	userService.updateUser(mapUserFormToUser(userForm, null));
    	
    	return "index";
    }
    
    /** Permission deny page */
    @RequestMapping("/permission-deny")
    public String PermissionDeny(Model model) {
        model.addAttribute("errorCode", "403");
        model.addAttribute("errorMessage", "You don't have permission to access this page.");
        return "error";
    }

    /** Simulation of an exception. */
    @RequestMapping("/simulateError")
    public void simulateError() {
        throw new RuntimeException("This is a simulated error message");
    }
    
    private User mapUserFormToUser(UserForm form, String role) {
    	User user = new User(form.getUsername(), form.getPassword(), role);
    	user.setEmail(form.getEmail());
    	user.setFirstname(form.getFirstname());
    	user.setLastname(form.getLastname());
    	user.setOrganization(form.getOrganization());
    	user.setCreateDate(new Date());
    	return user;
    }
    
    private UserForm mapUserToUserForm(User user) {
    	UserForm form = new UserForm();
    	form.setUsername(user.getUsername());
    	form.setEmail(user.getEmail());
    	form.setFirstname(user.getFirstname());
    	form.setLastname(user.getLastname());
    	form.setOrganization(user.getOrganization());
    	return form;
    }
}
