package jfang.project.timesheet.web.dto;

import java.util.List;

public class EmployeeAccountActionDto {

    private List<String> usernames;
    private String action;

    public List<String> getUsernames() {
        return usernames;
    }
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

}
