package net.island.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "etl_record")
public class ETLRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "table_name")
	private String table;
	
	@Column(name = "last_etl_time")
	private String lastETLTime;

	@Column(name = "last_transaction_time")
	private String lastTransactionTime;
	
	@Column(name = "etl_result")
	private String etlResult;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getLastETLTime() {
		return lastETLTime;
	}

	public void setLastETLTime(String lastETLTime) {
		this.lastETLTime = lastETLTime;
	}

	public String getLastTransactionTime() {
		return lastTransactionTime;
	}

	public void setLastTransactionTime(String lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	public String getEtlResult() {
		return etlResult;
	}

	public void setEtlResult(String etlResult) {
		this.etlResult = etlResult;
	}
	
}
