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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class MeasurementResource {

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private MeasurementRepository measurementRepository;
	

	public LocalDateTime getDateTime() {
		LocalDateTime datetime = LocalDateTime.now();
		return datetime;
	}
	
	//Get data default by last 15 minutes
	@RequestMapping(value = "/measurements",
	produces = MediaType.APPLICATION_JSON_VALUE,
	method = RequestMethod.GET)
	//@GetMapping("/measurements")
	public List<MeasurementEntity> retriveByTime(@RequestParam(value = "time",  required = false, defaultValue = "0") String time) {
		String substring = time.substring(0,1);

		long i = Long.parseLong(substring);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		LocalDateTime datetime = LocalDateTime.now();
		datetime = datetime.minusHours(i);
		String aftersubtraction = datetime.format(formatter);

		if(time == time) {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		LocalDateTime datetime1 = LocalDateTime.now();
		datetime = datetime.minusMinutes(15);
		String aftersubtraction1 = datetime.format(formatter);
		
		return measurementRepository.findAllByTimeBetween(datetime);
		
		}else {
			System.out.println("yes im here");			
			return measurementRepository.findAllByPublicationTimeBetween(datetime);
		}
	}
	
	//Get data by Id
	@GetMapping("/measurement/{id}")
	public List<MeasurementEntity> retrieveById(@PathVariable Long id) {
		return measurementRepository.findByIds(id);
	}
	
	//Get data by hours
//	@GetMapping("/measurement")
//	public List<MeasurementEntity> retriveByTimes(@RequestParam(value = "time") String time) {
//		
//		
//		String substring = time.substring(0,1);
//
//		long i = Long.parseLong(substring);
//		System.out.print(substring);
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
//		LocalDateTime datetime = LocalDateTime.now();
//		datetime = datetime.minusHours(i);
//		String aftersubtraction = datetime.format(formatter);
//
//		return measurementRepository.findAllByPublicationTimeBetween(datetime);
//		
//	}

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


//@RequestMapping(value = "/time",
//produces = { "text/plain" },
//method = RequestMethod.GET)
//public ResponseEntity<String> getTime(@RequestParam(value = "delta",
//                                     required = false,
//                                     defaultValue = "0")
//                                     long delta) {
//if (0L == delta) {
//return new ResponseEntity<String>(calcTime(), HttpStatus.OK);
//}
//else {
//return new ResponseEntity<String>(calcTime(delta), HttpStatus.OK);
//}
//}

//@GetMapping("/measurement")
//
//
//public List<MeasurementEntity> retriveByPublicationTimeBetween() {
//  return measurementRepository.findAllByPublicationTimeBetween(getDateTime());
//}