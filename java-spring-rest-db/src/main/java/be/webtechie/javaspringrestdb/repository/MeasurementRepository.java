package be.webtechie.javaspringrestdb.repository;

import be.webtechie.javaspringrestdb.entity.MeasurementEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends CrudRepository<MeasurementEntity, Long> {
//@Query("SELECT u FROM measurements u")
//Page<MeasurementEntity>findAllUsers(Pageable pageable);
//List<MeasurementEntity> getAllAddedAtLeastXHoursAgo(@Param("sensorId") long sensorId);
//@Query("SELECT u FROM MeasurementEntity u where u.id=?1")

	Page<MeasurementEntity> findAll(Pageable pageable);

	@Query("SELECT u FROM MeasurementEntity u where u.id=:id")
	List<MeasurementEntity> findByIds(Long id);

	@Query("select u from MeasurementEntity u where u.timestamp1>:datetime")
	List<MeasurementEntity> findAllByPublicationTimeBetween(LocalDateTime datetime);
	
	@Query("select a from MeasurementEntity a where a.timestamp1>:datetime")
	List<MeasurementEntity>findAllByTimeBetween(LocalDateTime datetime);

}