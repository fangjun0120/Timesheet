package jfang.project.timesheet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 562592436121827373L;

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long userId;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL", nullable = true, length = 64)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 64)
    private String password;

    @Column(name = "ORGANIZATION", nullable = true)
    private String organization;
    
    @Column(name = "FIRST_NAME", nullable = true)
    private String firstname;

    @Column(name = "LAST_NAME", nullable = true)
    private String lastname;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT", nullable = false)
    private Date createDate;

    @Column(name = "ROLE", nullable = false)
    private String role;
    
    @Type(type="yes_no")
    @Column(name = "ENABLE", nullable = false)
    private boolean enabled;
    
    @Type(type="yes_no")
    @Column(name = "EXPIRED", nullable = false)
    private boolean expired;

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createDate = new Date();
        this.enabled = true;
        this.expired = false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", username=" + username + ", email=" + email
                + ", password=" + password + ", organization=" + organization
                + ", firstname=" + firstname + ", lastname=" + lastname
                + ", createDate=" + createDate + ", role=" + role
                + ", enabled=" + enabled + ", expired=" + expired + "]";
    }

}
