package net.island.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attendance_details")
public class AttendanceDetails {

	@Id
	@Column(name = "employee_id")
	private String id;
	
	private String name;
	
	private String date;
	
	@Column(name = "leave_start_time")
	private String leaveStartTime;
	
	@Column(name = "leave_end_time")
	private String leaveEndTime;
	
	@Column(name = "num_of_leave")
	private int numOfLeave;
	
	@Column(name = "leave_hours")
	private long leaveHours;
	
	@Column(name = "overtime_start_time")
	private String overtimeStartTime;
	
	@Column(name = "overtime_end_time")
	private String overtimeEndTime;
	
	@Column(name = "num_of_overtime")
	private int numOfOvertime;
	
	@Column(name = "overtime_hours")
	private long overtimeHours;
	
	@Column(name = "punch_in")
	private String punchIn;
	
	@Column(name = "punch_out")
	private String punchOut;
	
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
	public String getLeaveStartTime() {
		return leaveStartTime;
	}
	public void setLeaveStartTime(String leaveStartTime) {
		this.leaveStartTime = leaveStartTime;
	}
	public String getLeaveEndTime() {
		return leaveEndTime;
	}
	public void setLeaveEndTime(String leaveEndTime) {
		this.leaveEndTime = leaveEndTime;
	}
	public int getNumOfLeave() {
		return numOfLeave;
	}
	public void setNumOfLeave(int numOfLeave) {
		this.numOfLeave = numOfLeave;
	}
	public long getLeaveHours() {
		return leaveHours;
	}
	public void setLeaveHours(long leaveHours) {
		this.leaveHours = leaveHours;
	}
	public String getOvertimeStartTime() {
		return overtimeStartTime;
	}
	public void setOvertimeStartTime(String overtimeStartTime) {
		this.overtimeStartTime = overtimeStartTime;
	}
	public String getOvertimeEndTime() {
		return overtimeEndTime;
	}
	public void setOvertimeEndTime(String overtimeEndTime) {
		this.overtimeEndTime = overtimeEndTime;
	}
	public int getNumOfOvertime() {
		return numOfOvertime;
	}
	public void setNumOfOvertime(int numOfOvertime) {
		this.numOfOvertime = numOfOvertime;
	}
	public long getOvertimeHours() {
		return overtimeHours;
	}
	public void setOvertimeHours(long overtimeHours) {
		this.overtimeHours = overtimeHours;
	}
	public String getPunchIn() {
		return punchIn;
	}
	public void setPunchIn(String punchIn) {
		this.punchIn = punchIn;
	}
	public String getPunchOut() {
		return punchOut;
	}
	public void setPunchOut(String punchOut) {
		this.punchOut = punchOut;
	}
	
}
