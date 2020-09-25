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

import net.island.spring.entity.Attendance;
import net.island.spring.entity.AttendanceReport;
import net.island.spring.repository.AttendanceRepository;

@Service
public class AttendanceReportService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	public List<AttendanceReport> getAttendanceReport(String startDate, String endDate) throws ParseException {
		List<Attendance> attendanceList = getAttendanceList(startDate, endDate);
		List<String> employeeIdList = getEmployeeIdList(attendanceList);
		List<String> dateList = getDateList(startDate, endDate);
		List<AttendanceReport> attendanceReportList = new ArrayList<>();
		
		for (String id:employeeIdList) {
			for (String date:dateList) {
				// 某人在某天的所有出勤紀錄 包含請假和加班
				List<Attendance> dailyList = getAttendanceByIdAndDate(id, date);
				
				// 可形成一條報表資料
				AttendanceReport attre = getDailyReport(dailyList);
				
				attendanceReportList.add(attre);
			}
		}
		
		return attendanceReportList;
	}
	
	private List<Attendance> getAttendanceList(String startDate, String endDate) {
		return attendanceRepository.findByDateBetween(startDate, endDate);
	}
	
	private List<String> getEmployeeIdList(List<Attendance> source) {
		HashSet<String> set = new HashSet<>();
		List<String> result = new ArrayList<>();
		
		for (Attendance a:source) {
			set.add(a.getEmployeeId());
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
	
	private List<Attendance> getAttendanceByIdAndDate(String id, String date) {
		return attendanceRepository.findByIdAndDate(id, date);
	}
	
	private AttendanceReport getDailyReport(List<Attendance> dailyList) {
		
		AttendanceReport report = new AttendanceReport();
		
		if (dailyList.size() > 0) {
			report.setId(dailyList.get(0).getEmployeeId());
			report.setName(dailyList.get(0).getName());
			report.setDate(dailyList.get(0).getDate());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long leavetime = 0;
			long overtime = 0;
			
			for (Attendance att:dailyList) {
				
				try {
					String startDateTime = report.getDate() + " " + att.getStartTime();
					String endDateTime = report.getDate() + " " + att.getEndTime();
					Date startTime = df.parse(startDateTime);
					Date endTime = df.parse(endDateTime);
					
					long between = endTime.getTime() - startTime.getTime();
					long hour = between / (60 * 60 * 1000);
					
					if (att.getType().equals("請假")) {
						leavetime += hour;
					} else if (att.getType().equals("加班")) {
						overtime += hour;
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			
			report.setLeaveTime(leavetime);
			report.setOvertime(overtime);
		}
		
		return report;
	}
	
	
}
