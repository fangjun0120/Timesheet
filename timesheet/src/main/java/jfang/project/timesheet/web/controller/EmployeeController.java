package jfang.project.timesheet.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import jfang.project.timesheet.constant.Constants;
import jfang.project.timesheet.constant.ResponseStatus;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.WeekSheet;
import jfang.project.timesheet.service.HumanResourceService;
import jfang.project.timesheet.service.ProjectService;
import jfang.project.timesheet.service.TimesheetService;
import jfang.project.timesheet.utility.StringProecessUtil;
import jfang.project.timesheet.web.dto.AjaxResponseStatus;
import jfang.project.timesheet.web.dto.WeekSheetPostDto;
import jfang.project.timesheet.web.dto.WeekSheetQueryReqDto;
import jfang.project.timesheet.web.dto.WeekSheetQueryResDto;

import jfang.project.timesheet.web.exception.ResourceNotFoundException;
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

	/**
	 * GET method to home page.
	 *
	 * @return
     */
	@RequestMapping("/")
    public String userIndex() {
        return "index";
    }

	/**
	 * GET method to timesheet page.
	 *
	 * @return
     */
	@RequestMapping("/timesheet")
	public String getWeekSheetPage(Model model) {
		Employee employee = getCurrentEmployee();
		List<String> list = projectService.getProjectListByEmployee(employee);
		if (list == null || list.size() == 0) {
			throw new IllegalArgumentException("No project found.");
		}
		model.addAttribute("projectList", list);
	    return "user/timesheet";
	}

	/**
	 * AJAX
	 * POST method to query weeksheet by startDate, employee and project name.
	 *
	 * @param requestDto
	 * @return
     */
	@ResponseBody
    @RequestMapping(value="/timesheet/date", method=RequestMethod.POST)
    public WeekSheetQueryResDto ajaxGetWeekSheetData(@RequestBody WeekSheetQueryReqDto requestDto) {
		logger.debug("ajax request start date: " + requestDto.getDateString());
		WeekSheet weekSheet = timesheetService.getWeekSheetByDate(
				requestDto.getDateString(), getCurrentEmployee(), "proj1");
		WeekSheetQueryResDto weekSheetDto = mapWeekSheetToDTO(weekSheet);
		logger.debug("ajax response: " + weekSheetDto.toString());
		return weekSheetDto;
	}

	/**
	 * AJAX
	 * POST method to upadte weeksheet data.
	 *
	 * @param requestDto
	 * @return
     */
	@ResponseBody
    @RequestMapping(value="/timesheet/submit", method=RequestMethod.POST)
    public AjaxResponseStatus ajaxSubmitWeekSheetData(@RequestBody WeekSheetPostDto requestDto) {
		logger.info("WeekSheet DTO: " + requestDto.toString());
		List<Integer> hours = new ArrayList<Integer>();
		hours.add(requestDto.getSunHours());
		hours.add(requestDto.getMonHours());
		hours.add(requestDto.getTueHours());
		hours.add(requestDto.getWedHours());
		hours.add(requestDto.getThuHours());
		hours.add(requestDto.getFriHours());
		hours.add(requestDto.getSatHours());
		
		boolean result = timesheetService.saveWeekSheet(getCurrentEmployee(), "proj1", requestDto.getStartDate(), hours);
		AjaxResponseStatus response = new AjaxResponseStatus();
		if (result) {
			response.setStatus(ResponseStatus.SUCCESS.value());
			response.setMessage("Timesheet updated successfully.");
		}
		else {
			response.setStatus(ResponseStatus.ERROR.value());
			response.setMessage("Failed to update timesheet.");
		}
		return response;
	}

	/**
	 * AJAX
	 * POST method to unsubmit weeksheet.
	 *
	 * @param requestDto
	 * @return
     */
	@ResponseBody
    @RequestMapping(value="/timesheet/unsubmit", method=RequestMethod.POST)
    public AjaxResponseStatus ajaxUnsubmitWeekSheetData(@RequestBody WeekSheetQueryReqDto requestDto) {
		boolean result = timesheetService.unsubmitWeekSheet(requestDto.getDateString(), getCurrentEmployee(), "proj1");
		
		AjaxResponseStatus response = new AjaxResponseStatus();
		if (result) {
			response.setStatus(ResponseStatus.SUCCESS.value());
			response.setMessage("Timesheet unsubmitted.");
		}
		else {
			response.setStatus(ResponseStatus.ERROR.value());
			response.setMessage("Failed to unsubmit timesheet.");
		}
		return response;
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
    
    private WeekSheetQueryResDto mapWeekSheetToDTO(WeekSheet weekSheet) {
    	WeekSheetQueryResDto dto = mapper.map(weekSheet, WeekSheetQueryResDto.class);
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
