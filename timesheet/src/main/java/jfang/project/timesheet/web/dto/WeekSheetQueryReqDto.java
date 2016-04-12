package jfang.project.timesheet.web.dto;

import java.io.Serializable;

public class WeekSheetQueryReqDto implements Serializable {

    private static final long serialVersionUID = 8652554695060368609L;

    private String employeeName;
    private String projectName;
    private String dateString;

    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getDateString() {
        return dateString;
    }
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "WeekSheetQueryReqDto{" +
                "employeeName='" + employeeName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", dateString='" + dateString + '\'' +
                '}';
    }
}
