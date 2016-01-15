package jfang.project.timesheet.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.config.ServiceConfig;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.WeekSheet;
import jfang.project.timesheet.repository.EmployeeRepository;
import jfang.project.timesheet.repository.WeekSheetRepository;
import jfang.project.timesheet.utility.StringProecessUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TimesheetServiceTest {

	@Resource
	private TimesheetService timesheetService;
	
	@Resource
	private EmployeeRepository employeeRepository;
	
	@Resource
	private WeekSheetRepository weekSheetRepository;

	@Test
	public void testSaveWeekSheet() {
		Integer[] hours = {1,2,3,4,5,6,7};
		Employee employee = employeeRepository.findByUsername("emp1");
		timesheetService.saveWeekSheet(employee, "bsscores", "2016/01/10", Arrays.asList(hours));
		Date startDate = StringProecessUtil.StringToDate("2016/01/10");
		WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(startDate, 1, 1);
		assertNotNull(weekSheet);
	}
	
	@Test
	public void testUpdate() {
		Integer[] hours = {1,2,3,4,5,6,7};
		Employee employee = employeeRepository.findByUsername("emp1");
		timesheetService.saveWeekSheet(employee, "bsscores", "2016/01/03", Arrays.asList(hours));
		Date startDate = StringProecessUtil.StringToDate("2016/01/03");
		WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(startDate, 1, 1);
		assertEquals(1, weekSheet.getWeekSheetId());
		assertEquals(1, weekSheet.getSheets().get(0).getHour());
		assertEquals(2, weekSheet.getSheets().get(1).getHour());
		assertEquals(3, weekSheet.getSheets().get(2).getHour());
		assertEquals(4, weekSheet.getSheets().get(3).getHour());
		assertEquals(5, weekSheet.getSheets().get(4).getHour());
		assertEquals(6, weekSheet.getSheets().get(5).getHour());
		assertEquals(7, weekSheet.getSheets().get(6).getHour());
	}
	
}
