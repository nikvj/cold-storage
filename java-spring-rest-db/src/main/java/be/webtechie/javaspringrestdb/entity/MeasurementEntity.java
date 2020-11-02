package be.webtechie.javaspringrestdb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MEASUREMENTS", uniqueConstraints = {
		@UniqueConstraint(name = "UN_MEASUREMENT_ID", columnNames = { "ID" }) })
public class MeasurementEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "SENSOR_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_MEASUREMENT_SENSOR"))
	private SensorEntity sensorEntity;

	@Column(name = "time_stamp")
	private long timestamp;

	@Column(name = "unit_key")
	private String key;

	@Column(name = "unit_value")
	private double value;

//    @Column(name="time",nullable = false, updatable=false, insertable= false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Column(name = "time")
	private LocalDateTime timestamp1;

	public MeasurementEntity() {
		// NOP
	}

	public MeasurementEntity(SensorEntity sensorEntity, long timestamp, String key, double value) {
		this.sensorEntity = sensorEntity;
		this.timestamp = timestamp;
		this.key = key;
		this.value = value;
		LocalDateTime datetime = LocalDateTime.now();
		this.timestamp1 = datetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public SensorEntity getSensor() {
		return sensorEntity;
	}

	public void setSensor(SensorEntity sensorEntity) {
		this.sensorEntity = sensorEntity;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
