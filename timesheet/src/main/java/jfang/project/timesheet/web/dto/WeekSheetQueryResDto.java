package jfang.project.timesheet.web.dto;

import java.io.Serializable;

public class WeekSheetQueryResDto implements Serializable {

	private static final long serialVersionUID = 5708196924289807814L;

	private String sunDate;
	private int sunHours;
	private String monDate;
	private int monHours;
	private String tueDate;
	private int tueHours;
	private String wedDate;
	private int wedHours;
	private String thuDate;
	private int thuHours;
	private String friDate;
	private int friHours;
	private String satDate;
	private int satHours;
	
	private int totalHours;
	
	private boolean submitted;
	private boolean approved;
	
	public String getSunDate() {
		return sunDate;
	}
	public void setSunDate(String sunDate) {
		this.sunDate = sunDate;
	}
	public int getSunHours() {
		return sunHours;
	}
	public void setSunHours(int sunHours) {
		this.sunHours = sunHours;
	}
	public String getMonDate() {
		return monDate;
	}
	public void setMonDate(String monDate) {
		this.monDate = monDate;
	}
	public int getMonHours() {
		return monHours;
	}
	public void setMonHours(int monHours) {
		this.monHours = monHours;
	}
	public String getTueDate() {
		return tueDate;
	}
	public void setTueDate(String tueDate) {
		this.tueDate = tueDate;
	}
	public int getTueHours() {
		return tueHours;
	}
	public void setTueHours(int tueHours) {
		this.tueHours = tueHours;
	}
	public String getWedDate() {
		return wedDate;
	}
	public void setWedDate(String wedDate) {
		this.wedDate = wedDate;
	}
	public int getWedHours() {
		return wedHours;
	}
	public void setWedHours(int wedHours) {
		this.wedHours = wedHours;
	}
	public String getThuDate() {
		return thuDate;
	}
	public void setThuDate(String thuDate) {
		this.thuDate = thuDate;
	}
	public int getThuHours() {
		return thuHours;
	}
	public void setThuHours(int thuHours) {
		this.thuHours = thuHours;
	}
	public String getFriDate() {
		return friDate;
	}
	public void setFriDate(String friDate) {
		this.friDate = friDate;
	}
	public int getFriHours() {
		return friHours;
	}
	public void setFriHours(int friHours) {
		this.friHours = friHours;
	}
	public String getSatDate() {
		return satDate;
	}
	public void setSatDate(String satDate) {
		this.satDate = satDate;
	}
	public int getSatHours() {
		return satHours;
	}
	public void setSatHours(int satHours) {
		this.satHours = satHours;
	}
	public int getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
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
		return "WeekSheetDto [sunDate=" + sunDate + ", sunHours=" + sunHours
				+ ", monDate=" + monDate + ", monHours=" + monHours
				+ ", tueDate=" + tueDate + ", tueHours=" + tueHours
				+ ", wedDate=" + wedDate + ", wedHours=" + wedHours
				+ ", thuDate=" + thuDate + ", thuHours=" + thuHours
				+ ", friDate=" + friDate + ", friHours=" + friHours
				+ ", satDate=" + satDate + ", satHours=" + satHours
				+ ", totalHours=" + totalHours + ", submitted=" + submitted
				+ ", approved=" + approved + "]";
	}
	
}
