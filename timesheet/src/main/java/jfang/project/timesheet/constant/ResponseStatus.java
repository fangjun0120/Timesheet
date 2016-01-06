package jfang.project.timesheet.constant;

public enum ResponseStatus {

	SUCCESS("success"), 
	INFO("info"), 
	ERROR("error");
	
	private String status;
	
	private ResponseStatus(String status) {
		this.status = status;
	}
	
	public String value() {
		return status;
	}
}
