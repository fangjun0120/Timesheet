package jfang.project.timesheet.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import jfang.project.timesheet.config.DataAccessConfig;
import jfang.project.timesheet.config.DataSourceConfig;
import jfang.project.timesheet.model.DaySheet;
import jfang.project.timesheet.model.Employee;
import jfang.project.timesheet.model.Project;
import jfang.project.timesheet.model.WeekSheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DataAccessConfig.class,
        DataSourceConfig.class})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class WeekSheetRepositoryTest {

    @Resource
    private WeekSheetRepository weekSheetRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private ProjectRepository projectRepository;

    private Date getDateByString(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date re = null;
        try {
            re = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re;
    }

    @Test
    public void testFindByStartDateAndEmpAndProj() {
        WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(getDateByString("2015-12-06"), 1l, 1l);
        assertEquals(1, weekSheet.getWeekSheetId());
        int day = 1;
        Calendar cal = Calendar.getInstance();
        for (DaySheet daySheet: weekSheet.getSheets()) {
            cal.setTime(daySheet.getDate());
            assertEquals(day, cal.get(Calendar.DAY_OF_WEEK));
            day++;
        }
    }

    @Test
    public void testNotFound() {
        WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(getDateByString("2015-01-01"), 1l, 1l);
        assertNull(weekSheet);
    }

    @Test
    public void testSaveNew() {
        Employee employee = employeeRepository.findByUsername("emp1");
        Project project = projectRepository.findByName("bsscores");
        WeekSheet weekSheet = new WeekSheet(employee, project);
        Date date = getDateByString("2015-02-02");
        weekSheet.setStartDate(date);
        DaySheet daySheet = new DaySheet(date, 1);
        daySheet.setWeekSheet(weekSheet);
        weekSheet.setTotalHour(1);
        List<DaySheet> sheets = new ArrayList<DaySheet>();
        sheets.add(daySheet);
        weekSheet.setSheets(sheets);

        weekSheet = weekSheetRepository.save(weekSheet);
        assertNotNull(weekSheet.getWeekSheetId());
        assertNotNull(weekSheet.getSheets().get(0).getDaySheetId());
    }

    @Test
    public void testUpdate() {
        WeekSheet weekSheet = weekSheetRepository.findByStartDateAndEmployeeIdAndProjectId(getDateByString("2015-12-06"), 1l, 1l);
        weekSheet.setTotalHour(100);
        weekSheet = weekSheetRepository.save(weekSheet);
        assertEquals(100, weekSheet.getTotalHour());
    }
}
