package net.island.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "employee_id")
	private String employeeId;
	private String name;
	private String date;
	private String type;
	private String leave;
	@Column(name = "start_time")
	private String startTime;
	@Column(name = "end_time")
	private String endTime;
	@Column(name = "transaction_time")
	private String transactionTime;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLeave() {
		return leave;
	}
	
	public void setLeave(String leave) {
		this.leave = leave;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getTransactionTime() {
		return transactionTime;
	}
	
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", employeeId=" + employeeId + ", name=" + name + ", date=" + date + ", type="
				+ type + ", leave=" + leave + ", startTime=" + startTime + ", endTime=" + endTime + ", transactionTime="
				+ transactionTime + "]";
	}
	
}
