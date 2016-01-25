package jfang.project.timesheet.web.dto;

import java.util.List;

/**
 * Created by jfang on 1/25/16.
 */
public class ProjSelectedEmpDto {

    private String projectName;
    private List<String> employeeNameList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getEmployeeNameList() {
        return employeeNameList;
    }

    public void setEmployeeNameList(List<String> employeeNameList) {
        this.employeeNameList = employeeNameList;
    }
}
