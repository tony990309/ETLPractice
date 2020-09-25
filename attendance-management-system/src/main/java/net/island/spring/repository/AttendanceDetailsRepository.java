package net.island.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.island.spring.entity.AttendanceDetails;

@Repository
public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, Long> {
	
}
