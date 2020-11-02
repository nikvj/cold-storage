package be.webtechie.javaspringrestdb.resource;

import be.webtechie.javaspringrestdb.entity.MeasurementEntity;
import be.webtechie.javaspringrestdb.entity.SensorEntity;
import be.webtechie.javaspringrestdb.repository.MeasurementRepository;
import be.webtechie.javaspringrestdb.repository.SensorRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class MeasurementResource {

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private MeasurementRepository measurementRepository;

	@GetMapping("/measurements")
	public List<MeasurementEntity> retriveByTime() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		LocalDateTime datetime = LocalDateTime.now();
		datetime = datetime.minusMinutes(15);
		String aftersubtraction = datetime.format(formatter);

		return measurementRepository.findAllByTimeBetween(datetime);

	}

	@GetMapping("/measurement/{id}")
	public List<MeasurementEntity> retrieveById(@PathVariable Long id) {
		return measurementRepository.findByIds(id);
	}

	public LocalDateTime getDateTime() {

		LocalDateTime datetime = LocalDateTime.now();
//		datetime = datetime.minusMinutes(15);

		return datetime;

	}

	@GetMapping("/measurement")
	public List<MeasurementEntity> retriveByTimes(@RequestParam(value = "time") String time) {

		String substring = time.substring(0, 1);

		long i = Long.parseLong(substring);
		System.out.print(substring);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		LocalDateTime datetime = LocalDateTime.now();
		datetime = datetime.minusHours(i);
		String aftersubtraction = datetime.format(formatter);

		return measurementRepository.findAllByPublicationTimeBetween(datetime);
	}

	@PostMapping("/measurement")
	public ResponseEntity createMeasurement(@RequestParam long sensorId, @RequestParam String key,
			@RequestParam double value) {

		SensorEntity sensorEntity = sensorRepository.findById(sensorId);

		if (sensorEntity == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No sensor defined with the ID: " + sensorId);
		}

		MeasurementEntity measurementEntity = new MeasurementEntity(sensorEntity, System.currentTimeMillis(), key,
				value);
		measurementRepository.save(measurementEntity);

		return ResponseEntity.ok().build();
	}
}

//@GetMapping("/measurement")
//
//
//public List<MeasurementEntity> retriveByPublicationTimeBetween() {
//  return measurementRepository.findAllByPublicationTimeBetween(getDateTime());
//}