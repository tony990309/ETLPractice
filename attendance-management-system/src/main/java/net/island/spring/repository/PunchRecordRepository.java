package net.island.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.island.spring.entity.PunchRecord;

@Repository
public interface PunchRecordRepository extends JpaRepository<PunchRecord, Long> {
	
	@Query(nativeQuery=true, value="SELECT * FROM punch_record WHERE date>=?1 and date<=?2")
	List<PunchRecord> findByTransactionTime(String start, String end);

	@Query(nativeQuery=true, value="SELECT * FROM punch_record WHERE employee_id=?1 AND date=?2")
	List<PunchRecord> findByIdAndDate(String id, String date);
	
}
