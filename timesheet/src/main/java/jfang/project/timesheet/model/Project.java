package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = -8581019159168298913L;

	@Id
    @GeneratedValue
    @Column(name = "PROJECT_ID", unique = true, nullable = false)
	private long projectId;
	
	@Column(name = "PROJECT_NAME", nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="MANAGER_ID")
	private Manager manager;
	
	@ManyToMany
	@JoinTable(name = "EMPLOYEE_PROJECT", 
		joinColumns = @JoinColumn(name = "PROJECT_ID"), 
		inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
	private List<Employee> employees = new ArrayList<Employee>();;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", nullable = false)
	private Date endDate;
	
	public Project() {}

	public Project(String name, Manager manager, Date startDate, Date endDate) {
		this.name = name;
		this.manager = manager;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
