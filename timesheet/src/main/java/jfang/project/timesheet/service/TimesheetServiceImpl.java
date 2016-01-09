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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TimesheetServiceImpl implements TimesheetService {

	private final Logger logger = LoggerFactory.getLogger(TimesheetServiceImpl.class);
	
	@Resource
	private WeekSheetRepository weekSheetRepository;

	@Override
	public WeekSheet getWeekSheetByDate(Date date, Employee employee, Project project) {
		return weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(
				getFirstDayOfWeek(date), employee.getEmployeeId(), project.getProjectId());
	}
	
	@Override
	public WeekSheet getBlankWeekSheet(Date date, Employee employee, Project project) {
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
}
