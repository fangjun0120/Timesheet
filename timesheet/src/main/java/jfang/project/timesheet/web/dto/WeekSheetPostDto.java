package jfang.project.timesheet.web.dto;

public class WeekSheetPostDto {

	private String startDate;
	private String projectName;
	private int sunHours;
	private int monHours;
	private int tueHours;
	private int wedHours;
	private int thuHours;
	private int friHours;
	private int satHours;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getSunHours() {
		return sunHours;
	}
	public void setSunHours(int sunHours) {
		this.sunHours = sunHours;
	}
	public int getMonHours() {
		return monHours;
	}
	public void setMonHours(int monHours) {
		this.monHours = monHours;
	}
	public int getTueHours() {
		return tueHours;
	}
	public void setTueHours(int tueHours) {
		this.tueHours = tueHours;
	}
	public int getWedHours() {
		return wedHours;
	}
	public void setWedHours(int wedHours) {
		this.wedHours = wedHours;
	}
	public int getThuHours() {
		return thuHours;
	}
	public void setThuHours(int thuHours) {
		this.thuHours = thuHours;
	}
	public int getFriHours() {
		return friHours;
	}
	public void setFriHours(int friHours) {
		this.friHours = friHours;
	}
	public int getSatHours() {
		return satHours;
	}
	public void setSatHours(int satHours) {
		this.satHours = satHours;
	}

	@Override
	public String toString() {
		return "WeekSheetPostDto{" +
				"startDate='" + startDate + '\'' +
				", projectName='" + projectName + '\'' +
				", sunHours=" + sunHours +
				", monHours=" + monHours +
				", tueHours=" + tueHours +
				", wedHours=" + wedHours +
				", thuHours=" + thuHours +
				", friHours=" + friHours +
				", satHours=" + satHours +
				'}';
	}
}
