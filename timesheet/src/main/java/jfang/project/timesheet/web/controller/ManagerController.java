package jfang.project.timesheet.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.constant.ResponseStatus;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.User;
import jfang.project.timesheet.service.HumanResourceService;
import jfang.project.timesheet.service.ProjectService;
import jfang.project.timesheet.service.TimesheetService;
import jfang.project.timesheet.service.UserService;
import jfang.project.timesheet.web.dto.*;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("admin")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private Mapper mapper;

    @Resource
    private UserService userService;

    @Resource
    private HumanResourceService humanResourceService;

    @Resource
    private ProjectService projectService;

    @Resource
    private TimesheetService timesheetService;

    /**
     * GET method to employee management page.
     *
     * @return
     */
    @RequestMapping("/employee")
    public String getEmployees() {
        return "admin/users";
    }

    /**
     * AJAX
     * GET method to load employee name list for a given project.
     *
     * @param projectName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/timesheet/employee", produces = "application/json")
    public List<String> getEmployeeNameListByProject(@RequestParam("project") String projectName) {
        return humanResourceService.getEmployeeNameListByProjectName(projectName);
    }

    /**
     * AJAX
     * GET method to load all employees to current manager.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/employee/data", produces = "application/json")
    public List<EmployeeResponseDto> getEmployeeData() {
        List<EmployeeResponseDto> list = new ArrayList<EmployeeResponseDto>();
        Manager manager = humanResourceService.getCurrentManager();
        for (Employee employee: manager.getEmployees()) {
            list.add(mapUserToEmployee(employee.getUser()));
        }
        return list;
    }

    /**
     * AJAX
     * POST method to add new employee.
     * Return failure if the user name is already used.
     *
     * @param newEmployeeDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/employee/new", method=RequestMethod.POST)
    public AjaxResponseStatus addNewEmployee(@RequestBody NewEmployeeDto newEmployeeDto) {
        String username = newEmployeeDto.getUsername();
        String password = newEmployeeDto.getPassword();
        User user = new User(username, password, Constants.ROLE_EMPLOYEE);
        Long id = humanResourceService.registerNewEmployee(user);

        AjaxResponseStatus response = new AjaxResponseStatus();
        // username already exists
        if (id == 0) {
            response.setStatus(ResponseStatus.ERROR.value());
            response.setMessage("The user name is already used.");
        }
        else {
            response.setStatus(ResponseStatus.SUCCESS.value());
            response.setMessage("User created successfully. Please write down the credentials: " + username + " / " + password);
            logger.info(String.format("New employee added: %s / %s",  user.getUsername(), user.getPassword()));
        }

        return response;
    }

    /**
     * AJAX
     * POST method to reset password for selected employee(s)
     *
     * @param usernames
     * @return
     */
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

    /**
     * AJAX
     * POST method to lock employee(s)
     *
     * @param usernames
     * @return
     */
    // TODO: handle request to lock a locked user
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

    /**
     * GET method to project management page.
     * By default, select the first project if exists and load employee list.
     *
     * @return
     */
    @RequestMapping("/project")
    public ModelAndView getProjectPage() {
        Project project = projectService.findManagerProject();
        if (project == null) {
            ModelAndView mav = new ModelAndView("admin/project");
            mav.addObject("projectDto", null);
            return mav;
        }
        return loadProject(project);
    }

    /**
     * AJAX
     * GET method to load project list.
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/project/list", produces = "application/json")
    public List<String> getProjectList() {
        Manager manager = humanResourceService.getCurrentManager();
        List<String> list = projectService.getProjectListByManager(manager);
        logger.info(list.size() + " projects found.");
        return list;
    }

    /**
     * AJAX
     * POST method to add new project.
     * Return failure if the project name is already used.
     *
     * @param requestDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/project/new", method=RequestMethod.POST)
    public AjaxResponseStatus addNewProject(@RequestBody NewProjectDto requestDto) {
        Project project = mapper.map(requestDto, Project.class);
        long id = projectService.saveNewProject(project);

        AjaxResponseStatus response = new AjaxResponseStatus();
        // project name already exists
        if (id == 0) {
            response.setStatus(ResponseStatus.ERROR.value());
            response.setMessage("The project name is already used.");
        }
        else {
            response.setStatus(ResponseStatus.SUCCESS.value());
            response.setMessage("Project created successfully.");
        }
        return response;
    }

    /**
     * GET method to project management page.
     * Load project by name.
     *
     * @param name
     * @return
     */
    @RequestMapping("/project/{name}")
    public ModelAndView getProjectEmployeeList(@PathVariable String name) {
        Project project = projectService.findManagerProject(name);
        return loadProject(project);
    }

    /**
     * AJAX
     * POST method to update selected employees for the given project.
     *
     * @param requestDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/project/update", method=RequestMethod.POST)
    public AjaxResponseStatus updateEmployeeList(@RequestBody ProjSelectedEmpDto requestDto) {
        boolean res = projectService.updateProjectEmployeeList(requestDto.getProjectName(),
                requestDto.getEmployeeNameList());

        AjaxResponseStatus response = new AjaxResponseStatus();
        if (!res) {
            response.setStatus(ResponseStatus.ERROR.value());
            response.setMessage("Update failed.");
        }
        else {
            response.setStatus(ResponseStatus.SUCCESS.value());
            response.setMessage("Employee list updated successfully.");
        }
        return response;
    }

    /**
     * AJAX
     * POST method to approve an employee weeksheet.
     *
     * @param employeeName
     * @param projectName
     * @param startDate
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/timesheet/approve", method=RequestMethod.POST)
    public AjaxResponseStatus ajaxApproveWeekSheet(@RequestParam("employeeName") String employeeName,
                                                   @RequestParam("projectName") String projectName,
                                                   @RequestParam("startDate") String startDate) {
        logger.debug("Approve weeksheet: " + employeeName + " " + projectName + " " + startDate);

        Employee employee = humanResourceService.getEmployeeByRealName(employeeName);
        boolean res = timesheetService.approveWeekSheet(startDate, employee, projectName);
        AjaxResponseStatus response = new AjaxResponseStatus();
        if (!res) {
            response.setStatus(ResponseStatus.ERROR.value());
            response.setMessage("Approval failed.");
        }
        else {
            response.setStatus(ResponseStatus.SUCCESS.value());
            response.setMessage("WeekSheet approved successfully.");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value="/timesheet/disapprove", method=RequestMethod.POST)
    public AjaxResponseStatus ajaxDisapproveWeekSheet(@RequestParam("employeeName") String employeeName,
                                                   @RequestParam("projectName") String projectName,
                                                   @RequestParam("startDate") String startDate) {
        logger.debug("Disapprove weeksheet: " + employeeName + " " + projectName + " " + startDate);

        Employee employee = humanResourceService.getEmployeeByRealName(employeeName);
        boolean res = timesheetService.disapproveWeekSheet(startDate, employee, projectName);
        AjaxResponseStatus response = new AjaxResponseStatus();
        if (!res) {
            response.setStatus(ResponseStatus.ERROR.value());
            response.setMessage("Disapproval failed.");
        }
        else {
            response.setStatus(ResponseStatus.SUCCESS.value());
            response.setMessage("WeekSheet disapproved successfully.");
        }
        return response;
    }

    private ModelAndView loadProject(Project project) {
        ProjectDto projectDto = mapper.map(project, ProjectDto.class);

        // set selected employee names
        List<String> selectedList = new ArrayList<String>();
        Set<Long> selectedSet = new HashSet<Long>();
        for (Employee employee: project.getEmployees()) {
            String employeeName = employee.getUser().getFirstname()
                    + " " + employee.getUser().getLastname();
            selectedList.add(employeeName);
            selectedSet.add(employee.getEmployeeId());
        }
        projectDto.setSelectedList(selectedList);

        // select remaining employee names
        List<String> nonList = new ArrayList<String>();
        for (Employee employee: project.getManager().getEmployees()) {
            if (!selectedSet.contains(employee.getEmployeeId())) {
                String employeeName = employee.getUser().getFirstname()
                        + " " + employee.getUser().getLastname();
                nonList.add(employeeName);
            }
        }
        projectDto.setRemainList(nonList);

        ModelAndView mav = new ModelAndView("admin/project");
        mav.addObject("projectDto", projectDto);
        return mav;
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
