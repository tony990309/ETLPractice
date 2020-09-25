package net.island.spring.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.island.spring.entity.AttendanceDetails;
import net.island.spring.entity.AttendanceReport;
import net.island.spring.service.AttendanceReportService;
import net.island.spring.service.AttendanceDetailsService;

@RestController
public class AttendanceController {

	@Autowired
	private AttendanceReportService reportService;
	
	@Autowired
	private AttendanceDetailsService detailsService;
	
	@PostMapping("/attendance/report")
	public List<AttendanceReport> getAttendanceReport(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
		return reportService.getAttendanceReport(startDate, endDate);
	}

	@GetMapping("/attendance/details")
	public List<AttendanceDetails> excuteDetailsETL() throws ParseException {
		return detailsService.excuteDetailETL();
	}
	
	/*@GetMapping("/test")
	public void test() {
		reportService.test();
	}*/
	
	/*@PostMapping("/test/tp")
	public List<Attendance> testTP(@RequestParam String sd, @RequestParam String ed) {
		return attendanceService.getAttendanceList(sd, ed);
	}*/
	
}
