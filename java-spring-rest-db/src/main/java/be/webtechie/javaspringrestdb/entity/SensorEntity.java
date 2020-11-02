package be.webtechie.javaspringrestdb.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SENSORS", uniqueConstraints={@UniqueConstraint(name="UN_SENSOR_ID", columnNames={"ID"})})
public class SensorEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "sensorEntity",
            cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private Set<MeasurementEntity> measurements = new HashSet<>();

    public SensorEntity() {
        // NOP
    }

    public SensorEntity(String name) {
        this.name = name;
    }

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MeasurementEntity> getDataEntries() {
        return measurements;
    }

    public void setDataEntries(Set<MeasurementEntity> dataEntries) {
        this.measurements = dataEntries;
    }
}
