package jfang.project.timesheet.web.dto;

import java.util.List;

/**
 * Created by jfang on 1/23/16.
 */
public class ProjectDto {

    private String name;
    private String startDate;
    private String endDate;
    private List<String> remainList;
    private List<String> selectedList;

    public List<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<String> selectedList) {
        this.selectedList = selectedList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getRemainList() {
        return remainList;
    }

    public void setRemainList(List<String> remainList) {
        this.remainList = remainList;
    }
}
