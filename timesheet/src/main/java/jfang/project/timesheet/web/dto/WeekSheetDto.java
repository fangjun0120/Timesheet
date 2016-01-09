package jfang.project.timesheet.web.dto;

import java.io.Serializable;
import java.util.Date;

public class WeekSheetDto implements Serializable {

	private static final long serialVersionUID = 5708196924289807814L;

	private Date sunDate;
	private int sunHours;
	private Date monDate;
	private int monHours;
	private Date tueDate;
	private int tueHours;
	private Date wedDate;
	private int wedHours;
	private Date thuDate;
	private int thuHours;
	private Date friDate;
	private int friHours;
	private Date satDate;
	private int satHours;
	
	private int totalHours;

	public Date getSunDate() {
		return sunDate;
	}

	public void setSunDate(Date sunDate) {
		this.sunDate = sunDate;
	}

	public int getSunHours() {
		return sunHours;
	}

	public void setSunHours(int sunHours) {
		this.sunHours = sunHours;
	}

	public Date getMonDate() {
		return monDate;
	}

	public void setMonDate(Date monDate) {
		this.monDate = monDate;
	}

	public int getMonHours() {
		return monHours;
	}

	public void setMonHours(int monHours) {
		this.monHours = monHours;
	}

	public Date getTueDate() {
		return tueDate;
	}

	public void setTueDate(Date tueDate) {
		this.tueDate = tueDate;
	}

	public int getTueHours() {
		return tueHours;
	}

	public void setTueHours(int tueHours) {
		this.tueHours = tueHours;
	}

	public Date getWedDate() {
		return wedDate;
	}

	public void setWedDate(Date wedDate) {
		this.wedDate = wedDate;
	}

	public int getWedHours() {
		return wedHours;
	}

	public void setWedHours(int wedHours) {
		this.wedHours = wedHours;
	}

	public Date getThuDate() {
		return thuDate;
	}

	public void setThuDate(Date thuDate) {
		this.thuDate = thuDate;
	}

	public int getThuHours() {
		return thuHours;
	}

	public void setThuHours(int thuHours) {
		this.thuHours = thuHours;
	}

	public Date getFriDate() {
		return friDate;
	}

	public void setFriDate(Date friDate) {
		this.friDate = friDate;
	}

	public int getFriHours() {
		return friHours;
	}

	public void setFriHours(int friHours) {
		this.friHours = friHours;
	}

	public Date getSatDate() {
		return satDate;
	}

	public void setSatDate(Date satDate) {
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

	@Override
	public String toString() {
		return "WeekSheetDto [sunDate=" + sunDate + ", sunHours=" + sunHours
				+ ", monDate=" + monDate + ", monHours=" + monHours
				+ ", tueDate=" + tueDate + ", tueHours=" + tueHours
				+ ", wedDate=" + wedDate + ", wedHours=" + wedHours
				+ ", thuDate=" + thuDate + ", thuHours=" + thuHours
				+ ", friDate=" + friDate + ", friHours=" + friHours
				+ ", satDate=" + satDate + ", satHours=" + satHours
				+ ", totalHours=" + totalHours + "]";
	}
	
}
