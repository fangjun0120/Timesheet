package jfang.project.timesheet.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserForm implements Serializable {

	private static final long serialVersionUID = 7105363635741736595L;

    @NotEmpty(message = "{userform.username.notempty}")
	private String username;
	
    @NotEmpty(message = "{userform.password.notempty}")
    @Size(min = 8, message = "{userform.password.size}")
	private String password;
	
    @NotEmpty(message = "{userform.pwdconfirm.notempty}")
	private String pwdConfirm;
	
    @NotEmpty(message = "{userform.organization.notempty}")
    private String organization;
    
    @Pattern(regexp = "^$|.+@.+\\..+", message = "{userform.email.pattern}")
	private String email;
	
	private String firstname;
	
	private String lastname;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPwdConfirm() {
		return pwdConfirm;
	}
	public void setPwdConfirm(String pwdConfirm) {
		this.pwdConfirm = pwdConfirm;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	@Override
	public String toString() {
		return "UserForm [username=" + username + ", password=" + password
				+ ", pwdConfirm=" + pwdConfirm + ", organization="
				+ organization + ", email=" + email + ", firstname="
				+ firstname + ", lastname=" + lastname + "]";
	}
	
}
