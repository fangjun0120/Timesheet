package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MANAGER")
public class Manager implements Serializable {

    private static final long serialVersionUID = 2556200613089026431L;

    @Id
    @GeneratedValue
    @Column(name = "MANAGER_ID", unique = true, nullable = false)
    private long managerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(mappedBy="manager", fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<Employee>();

    @OneToMany(mappedBy="manager", fetch = FetchType.EAGER)
    private List<Project> projects = new ArrayList<Project>();

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Manager [managerId=" + managerId + ", user=" + user
                + ", employees=" + employees + ", projects=" + projects + "]";
    }

}
