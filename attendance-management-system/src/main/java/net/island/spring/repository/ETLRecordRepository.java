package net.island.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.island.spring.entity.ETLRecord;

@Repository
public interface ETLRecordRepository extends JpaRepository<ETLRecord, Long> {

	@Query(value = "select max(last_etl_time) from etl_record where etl_result='success'", nativeQuery = true)
	String findLast();

}
