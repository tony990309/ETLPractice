package net.island.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.island.spring.entity.ETLRecord;

@Repository
public interface ETLRecordRepository extends JpaRepository<ETLRecord, String> {

}
