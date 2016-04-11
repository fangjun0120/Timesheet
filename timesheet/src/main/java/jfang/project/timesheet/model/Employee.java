package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

	private static final long serialVersionUID = 598803739896116673L;

	@Id
    @GeneratedValue
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
	private long employeeId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="MANAGER_ID")
	private Manager manager;
	
	@ManyToMany(mappedBy="employees")
	private List<Project> projects = new ArrayList<Project>();

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
}
