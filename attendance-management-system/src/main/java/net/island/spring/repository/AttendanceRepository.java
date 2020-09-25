package net.island.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.island.spring.entity.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByDateBetween(String startDate, String endDate);
	
	@Query(nativeQuery=true, value="SELECT * FROM attendance WHERE employee_id=?1 AND date=?2")
	List<Attendance> findByIdAndDate(String id, String date);
	
	@Query(nativeQuery=true, value="SELECT * FROM attendance WHERE date<=?1")
	List<Attendance> findByTransactionTime(String date);
	
}
