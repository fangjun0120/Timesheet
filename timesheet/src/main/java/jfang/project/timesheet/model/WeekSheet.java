package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "WEEK_SHEET")
public class WeekSheet implements Serializable {

	private static final long serialVersionUID = 4661026806800030373L;
	
	@Id
    @GeneratedValue
    @Column(name = "WEEK_SHEET_ID", unique = true, nullable = false)
	private long weekSheetId;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
	private Date startDate;
	
	@OrderBy("date")
	@OneToMany(mappedBy="weekSheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DaySheet> sheets = new ArrayList<DaySheet>();
	
	@Column(name = "TOTAL_HOUR", nullable = false)
	private int totalHour;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	@Type(type="yes_no")
    @Column(name = "SUBMITTED_IND", nullable = false)
	private boolean submitted;
	
	@Type(type="yes_no")
    @Column(name = "APPROVED_IND", nullable = false)
	private boolean approved;

	public WeekSheet() {}
	
	public WeekSheet(Employee employee, Project project) {
		super();
		this.employee = employee;
		this.project = project;
		submitted = false;
		approved = false;
	}

	public long getWeekSheetId() {
		return weekSheetId;
	}

	public void setWeekSheetId(long weekSheetId) {
		this.weekSheetId = weekSheetId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<DaySheet> getSheets() {
		return sheets;
	}

	public void setSheets(List<DaySheet> sheets) {
		this.sheets = sheets;
	}

	public int getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(int totalHour) {
		this.totalHour = totalHour;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "WeekSheet [weekSheetId=" + weekSheetId + ", startDate="
				+ startDate + ", sheets=" + sheets + ", totalHour=" + totalHour
				+ ", employee=" + employee + ", project=" + project
				+ ", submitted=" + submitted + ", approved=" + approved + "]";
	}
	
}
