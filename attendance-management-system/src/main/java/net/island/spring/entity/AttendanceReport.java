package net.island.spring.entity;

public class AttendanceReport {

	private String id;
	private String name;
	private String date;
	private long leaveTime;
	private long overtime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}
	public long getOvertime() {
		return overtime;
	}
	public void setOvertime(long overtime) {
		this.overtime = overtime;
	}
	@Override
	public String toString() {
		return "AttendanceReport [id=" + id + ", name=" + name + ", date=" + date + ", leaveTime=" + leaveTime
				+ ", overtime=" + overtime + "]";
	}
	
}
