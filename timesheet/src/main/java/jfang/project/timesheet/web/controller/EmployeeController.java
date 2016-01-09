package jfang.project.timesheet.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.WeekSheet;
import jfang.project.timesheet.service.HumanResourceService;
import jfang.project.timesheet.service.ProjectService;
import jfang.project.timesheet.service.TimesheetService;
import jfang.project.timesheet.web.dto.WeekSheetDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jfang on 7/7/15.
 */
@Controller
@RequestMapping("user")
public class EmployeeController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Resource
	private HumanResourceService humanResourceService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private TimesheetService timesheetService;
	
	@RequestMapping("/")
    public String userIndex() {
        return "index";
    }

	@RequestMapping("/timesheet")
	public String getWeekSheetPage(Model model) {
		Employee employee = getCurrentEmployee(model);

		// get project from model
		Project project = projectService.getProjectByName("proj1");
		logger.debug("Looking for weeksheet for employee: " + employee.getUser().getUsername() 
				+ " project: " + project.getName());
		
		// get weeksheet by date
		WeekSheet weekSheet = timesheetService.getWeekSheetByDate(new Date(), employee, project);
		
		// if none, set initial values
		if (weekSheet == null) {
			weekSheet = timesheetService.getBlankWeekSheet(new Date(), employee, project);
		}
		
		WeekSheetDto weekSheetDto = mapWeekSheetToDTO(weekSheet);
		logger.debug(weekSheetDto.toString());
		model.addAttribute("weekSheetDto", weekSheetDto);
		
	    return "user/timesheet";
	}
	
    private Employee getCurrentEmployee(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        boolean isManager = false;
        for (GrantedAuthority role : auth.getAuthorities()) {
        	logger.debug("login as " + role.getAuthority());
            if (role.getAuthority().equals(Constants.ROLE_MANAGER))
            	isManager = true;
        }
        Employee employee = null;
        if (isManager) {
        	// get employee name from model
        }
        else {
        	employee = humanResourceService.getEmployeeByEmployeeName(name);
        }
        return employee;
    }
    
    private WeekSheetDto mapWeekSheetToDTO(WeekSheet weekSheet) {
    	WeekSheetDto dto = new WeekSheetDto();
    	dto.setSunDate(weekSheet.getSheets().get(0).getDate());
    	dto.setSunHours(weekSheet.getSheets().get(0).getHour());
    	dto.setMonDate(weekSheet.getSheets().get(1).getDate());
    	dto.setMonHours(weekSheet.getSheets().get(1).getHour());
    	dto.setTueDate(weekSheet.getSheets().get(2).getDate());
    	dto.setTueHours(weekSheet.getSheets().get(2).getHour());
    	dto.setWedDate(weekSheet.getSheets().get(3).getDate());
    	dto.setWedHours(weekSheet.getSheets().get(3).getHour());
    	dto.setThuDate(weekSheet.getSheets().get(4).getDate());
    	dto.setThuHours(weekSheet.getSheets().get(4).getHour());
    	dto.setFriDate(weekSheet.getSheets().get(5).getDate());
    	dto.setFriHours(weekSheet.getSheets().get(5).getHour());
    	dto.setSatDate(weekSheet.getSheets().get(6).getDate());
    	dto.setSatHours(weekSheet.getSheets().get(6).getHour());
    	dto.setTotalHours(weekSheet.getTotalHour());
    	return dto;
    }
}
