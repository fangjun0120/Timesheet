package jfang.project.timesheet.web.controller;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.WeekSheet;
import jfang.project.timesheet.service.HumanResourceService;
import jfang.project.timesheet.service.ProjectService;
import jfang.project.timesheet.service.TimesheetService;
import jfang.project.timesheet.utility.StringProecessUtil;
import jfang.project.timesheet.web.dto.WeekSheetDto;
import jfang.project.timesheet.web.dto.WeekSheetRequestDto;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jfang on 7/7/15.
 */
@Controller
@RequestMapping("user")
public class EmployeeController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private Mapper mapper;
	
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
		WeekSheetDto weekSheetDto = getWeekSheetByDate(new Date(), "");
		logger.debug(weekSheetDto.toString());
		model.addAttribute("weekSheetDto", weekSheetDto);
	    return "user/timesheet";
	}
	
	@ResponseBody
    @RequestMapping(value="/timesheet/date", method=RequestMethod.POST)
    public WeekSheetDto ajaxGetWeekSheetData(@RequestBody WeekSheetRequestDto requestDto) {
		logger.debug("ajax request start date: " + requestDto.getDateString());
		Date datePicked;
		try {
			datePicked = StringProecessUtil.StringToDate(requestDto.getDateString());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Wrong date format. Use yyyy/mm/dd.");
		}
		WeekSheetDto weekSheetDto = getWeekSheetByDate(datePicked, requestDto.getProjectName());
		logger.debug("ajax response: " + weekSheetDto.toString());
		return weekSheetDto;
	}
	
	private WeekSheetDto getWeekSheetByDate(Date date, String projectName) {
		Employee employee = getCurrentEmployee();

		// get project from model
		Project project = projectService.getProjectByName("proj1");
		logger.debug("Looking for weeksheet for employee: " + employee.getUser().getUsername() 
				+ " project: " + project.getName());
		
		// get weeksheet by date
		WeekSheet weekSheet = timesheetService.getWeekSheetByDate(date, employee, project);
		
		// if none, set initial values
		if (weekSheet == null) {
			weekSheet = timesheetService.getBlankWeekSheet(date, employee, project);
		}
		
		return mapWeekSheetToDTO(weekSheet);
	}
	
    private Employee getCurrentEmployee() {
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
    	WeekSheetDto dto = mapper.map(weekSheet, WeekSheetDto.class);
    	dto.setSunDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(0).getDate()));
    	dto.setSunHours(weekSheet.getSheets().get(0).getHour());
    	dto.setMonDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(1).getDate()));
    	dto.setMonHours(weekSheet.getSheets().get(1).getHour());
    	dto.setTueDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(2).getDate()));
    	dto.setTueHours(weekSheet.getSheets().get(2).getHour());
    	dto.setWedDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(3).getDate()));
    	dto.setWedHours(weekSheet.getSheets().get(3).getHour());
    	dto.setThuDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(4).getDate()));
    	dto.setThuHours(weekSheet.getSheets().get(4).getHour());
    	dto.setFriDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(5).getDate()));
    	dto.setFriHours(weekSheet.getSheets().get(5).getHour());
    	dto.setSatDate(StringProecessUtil.DateToString(weekSheet.getSheets().get(6).getDate()));
    	dto.setSatHours(weekSheet.getSheets().get(6).getHour());
    	return dto;
    }
}
