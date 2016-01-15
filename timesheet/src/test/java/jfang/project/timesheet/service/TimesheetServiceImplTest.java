package jfang.project.timesheet.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jfang.project.timesheet.model.DaySheet;
import jfang.project.timesheet.model.WeekSheet;

import org.junit.Test;

public class TimesheetServiceImplTest {

	private TimesheetServiceImpl service = new TimesheetServiceImpl();
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetBlankWeekSheet() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = formatter.parse("2015/12/29");
		WeekSheet weekSheet = service.getBlankWeekSheet(date, null, null);
		DaySheet first = weekSheet.getSheets().get(0);
		assertEquals(first.getDate().getDate(), 27);
	}
}
