package jfang.project.timesheet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import jfang.project.timesheet.model.DaySheet;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.WeekSheet;
import jfang.project.timesheet.repository.WeekSheetRepository;
import jfang.project.timesheet.utility.StringProecessUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TimesheetServiceImpl implements TimesheetService {

	private final Logger logger = LoggerFactory.getLogger(TimesheetServiceImpl.class);
	
	@Resource
	private WeekSheetRepository weekSheetRepository;

	@Resource
	private ProjectService projectService;
	
	@Override
	@Cacheable(value = "weekSheetCache", key = "{#dateString, #employee.employeeId, #projectName}")
	public WeekSheet getWeekSheetByDate(String dateString, Employee employee, String projectName) {
		logger.debug("Looking for weeksheet for employee: " + employee.getUser().getUsername() 
				+ " project: " + projectName);
		// get project name from view
		Project project = projectService.getProjectByName(projectName);

		// get weeksheet by date
		Date date;
		if (dateString == null) {
			date = new Date();
		}
		else {
			date = StringProecessUtil.StringToDate(dateString);
		}
		WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(
				getFirstDayOfWeek(date), employee.getEmployeeId(), project.getProjectId());

		// if none, set initial values
		if (weekSheet == null) {
			weekSheet = getBlankWeekSheet(date, employee, project);
		}

		return weekSheet;
	}
	
	@Override
	@CacheEvict(value = "weekSheetCache", key = "{#dateString, #employee.employeeId, #projectName}")
	public boolean saveWeekSheet(Employee employee, String projectName,
			String dateString, List<Integer> hours) {
		if (hours.size() != 7) {
			logger.error("Must have 7 day hours.");
			return false;
		}
		// get project name from view
		Project project = projectService.getProjectByName(projectName);
		
		Date date = StringProecessUtil.StringToDate(dateString);
		WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(
				getFirstDayOfWeek(date), employee.getEmployeeId(), project.getProjectId());
		// update existing weeksheet
		if (weekSheet != null) {
			for (int i = 0; i < 7; i++) {
				weekSheet.getSheets().get(i).setHour(hours.get(i));
			}
			weekSheet.setTotalHour(sumHours(hours));
			weekSheet.setSubmitted(true);
			weekSheetRepository.save(weekSheet);
			return true;
		}

		logger.info("No records found. Inserting new records ...");
		// insert new records
		weekSheet = new WeekSheet(employee, project);
		Date dateVar = date;
		List<DaySheet> daySheets = new ArrayList<DaySheet>();
		for (Integer hour: hours) {
			DaySheet sheet = new DaySheet(dateVar, hour);
			sheet.setWeekSheet(weekSheet);
			daySheets.add(sheet);
			dateVar = getNextDay(dateVar);
		}
		weekSheet.setSheets(daySheets);
		weekSheet.setStartDate(date);
		weekSheet.setTotalHour(sumHours(hours));
		weekSheet.setSubmitted(true);
		logger.info("udpate weeksheet: " + weekSheet);
		weekSheetRepository.save(weekSheet);
		
		return true;
	}

	@Override
	@CacheEvict(value = "weekSheetCache", key = "{#dateString, #employee.employeeId, #projectName}")
	public boolean unsubmitWeekSheet(String dateString, Employee employee, String projectName) {
		Project project = projectService.getProjectByName(projectName);

		Date date = StringProecessUtil.StringToDate(dateString);
		WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(
				getFirstDayOfWeek(date), employee.getEmployeeId(), project.getProjectId());
		weekSheet.setSubmitted(false);
		weekSheetRepository.save(weekSheet);
		return true;
	}
	
	WeekSheet getBlankWeekSheet(Date date, Employee employee, Project project) {
		WeekSheet weekSheet = new WeekSheet(employee, project);
		List<DaySheet> daySheets = new ArrayList<DaySheet>();
		Date dateVar = getFirstDayOfWeek(date);
		for (int i = 0; i < 7; i++) {
			daySheets.add(new DaySheet(dateVar, 0));
			dateVar = getNextDay(dateVar);
		}
		weekSheet.setSheets(daySheets);
		weekSheet.setTotalHour(0);
		return weekSheet;
	}
	
	private Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_WEEK, 1-index);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		return calendar.getTime();
	}
	
	private int sumHours(List<Integer> hours) {
		int sum = 0;
		for (Integer hour: hours) {
			sum += hour;
		}
		return sum;
	}
}
