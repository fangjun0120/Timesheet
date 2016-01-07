package jfang.project.timesheet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jfang on 7/7/15.
 */
@Controller
@RequestMapping("user")
public class EmployeeController {

	@RequestMapping("/")
    public String userIndex() {
        return "index";
    }

	@RequestMapping("/timesheet")
	public String timesheetIndex() {
	    return "timesheet";
	}
}
