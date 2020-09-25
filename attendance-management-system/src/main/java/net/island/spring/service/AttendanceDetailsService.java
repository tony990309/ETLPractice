package net.island.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.island.spring.entity.Attendance;
import net.island.spring.entity.AttendanceDetails;
import net.island.spring.entity.ETLRecord;
import net.island.spring.entity.PunchRecord;
import net.island.spring.repository.AttendanceDetailsRepository;
import net.island.spring.repository.AttendanceRepository;
import net.island.spring.repository.ETLRecordRepository;
import net.island.spring.repository.PunchRecordRepository;

@Service
public class AttendanceDetailsService {

	@Autowired
	private PunchRecordRepository punchRepository;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private AttendanceDetailsRepository attendanceDetailsRepository;
	
	@Autowired
	private ETLRecordRepository etlRecordRepository;
	
 	public List<AttendanceDetails> excuteDetailETL() throws ParseException {
 		
 		String startDate = getLastETLRecord();
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date now = new Date();
		String endDate = sdf.format(now);
 		
		List<PunchRecord> punchRecordList = getPunchRecords(startDate, endDate);
		List<String> employeeIdList = getEmployeeIdList(punchRecordList);
		List<String> dateList = getDateList(punchRecordList.get(0).getDate(), punchRecordList.get(punchRecordList.size()-1).getDate());
		List<AttendanceDetails> attendanceDetailsList = new ArrayList<>();
		
		for (String id:employeeIdList) {
			for (String date:dateList) {
				
				// 某人在某天的所有打卡紀錄及出勤
				List<PunchRecord> dailyPunchRecord = getPunchRecordByIdAndDate(id, date);
				List<Attendance> dailyAttendance = getAttendanceByIdAndDate(id, date);
				
				// 先確認筆數和打卡時間
				AttendanceDetails details = setPunchDetails(dailyPunchRecord);
				
				// 計算加班與請假時數
				AttendanceDetails temp = countHours(dailyAttendance);
				details.setLeaveStartTime(temp.getLeaveStartTime());
				details.setLeaveEndTime(temp.getLeaveEndTime());
				details.setLeaveHours(temp.getLeaveHours());
				details.setNumOfLeave(temp.getNumOfLeave());
				details.setOvertimeStartTime(temp.getOvertimeStartTime());
				details.setOvertimeEndTime(temp.getOvertimeEndTime());
				details.setOvertimeHours(temp.getOvertimeHours());
				details.setNumOfOvertime(temp.getNumOfOvertime());
				
				attendanceDetailsList.add(details);
			}
		}
		
		saveDetails(attendanceDetailsList, punchRecordList.get(punchRecordList.size()-1));
		
		return attendanceDetailsList;
	}

	private String getLastETLRecord() {
		return etlRecordRepository.findLast();
	}

	@Transactional(rollbackFor=Exception.class)
	public void saveDetails(List<AttendanceDetails> data, PunchRecord punchRecord) {
		ETLRecord etlRecord = new ETLRecord();
		etlRecord.setTable("attendance_details");
		
		for (AttendanceDetails d:data) {
			attendanceDetailsRepository.save(d);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date now = new Date();
		etlRecord.setLastETLTime(sdf.format(now));
		
		etlRecord.setLastTransactionTime(punchRecord.getTransactionTime());
		etlRecord.setEtlResult("success");
		
		etlRecordRepository.save(etlRecord);
	}
	
	private AttendanceDetails countHours(List<Attendance> source) {

		AttendanceDetails result = new AttendanceDetails();
		
		if (source.size() > 0) {
			
			result.setDate(source.get(0).getDate());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long leavehours = 0;
			long overtimehours = 0;
			int numOfLeave = 0;
			int numOfOvertime = 0;
			
			for (Attendance att:source) {
				
				try {
					String startDateTime = result.getDate() + " " + att.getStartTime();
					String endDateTime = result.getDate() + " " + att.getEndTime();
					Date startTime = df.parse(startDateTime);
					Date endTime = df.parse(endDateTime);
					
					long between = endTime.getTime() - startTime.getTime();
					long hour = between / (60 * 60 * 1000);
					
					if (att.getType().equals("請假")) {
						leavehours += hour;
						numOfLeave++;
						result.setLeaveStartTime(att.getStartTime());
						result.setLeaveEndTime(att.getEndTime());
						
					} else if (att.getType().equals("加班")) {
						overtimehours += hour;
						numOfOvertime++;
						result.setOvertimeStartTime(att.getStartTime());
						result.setOvertimeEndTime(att.getEndTime());
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			
			result.setLeaveHours(leavehours);
			result.setNumOfLeave(numOfLeave);
			result.setOvertimeHours(overtimehours);
			result.setNumOfOvertime(numOfOvertime);
		}
		
		return result;
	}

	private AttendanceDetails setPunchDetails(List<PunchRecord> source) {
		AttendanceDetails result = new AttendanceDetails();
		
		if (source.size() > 0) {
			result.setEmployeeId(source.get(0).getEmployeeId());
			result.setName(source.get(0).getName());
			result.setDate(source.get(0).getDate());
			
			for (PunchRecord p:source) {
				if (p.getType().equals("上班")) {
					result.setPunchIn(p.getPunchTime());
				} else if (p.getType().equals("下班")) {
					result.setPunchOut(p.getPunchTime());
				}
			}
		}
		
		return result;
	}

	private List<PunchRecord> getPunchRecords(String start, String end) {
		return punchRepository.findByTransactionTime(start, end);
	}
	
	private List<String> getEmployeeIdList(List<PunchRecord> source) {
		HashSet<String> set = new HashSet<>();
		List<String> result = new ArrayList<>();
		
		for (PunchRecord p:source) {
			set.add(p.getEmployeeId());
		}
		
		result.addAll(set);
		
		return result;
	}
	
	private List<String> getDateList(String startDate, String endDate) throws ParseException {
		List<String> days = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        Date temp = start;
        
        while (temp.before(end)) {
        	days.add(sdf.format(temp));
        	calendar.setTime(temp);
        	calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数往后推,负数往前移动 
        	temp = calendar.getTime();
        }
        
        days.add(sdf.format(end));
        
		return days;
	}
	
	private List<PunchRecord> getPunchRecordByIdAndDate(String id, String date) {
		return punchRepository.findByIdAndDate(id, date);
	}
	
	private List<Attendance> getAttendanceByIdAndDate(String id, String date) {
		return attendanceRepository.findByIdAndDate(id, date);
	}
	
}
