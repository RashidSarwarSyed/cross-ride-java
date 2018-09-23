/**
 * 
 */
package com.crossover.techtrial.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;

/**
 * RideController for Ride related APIs.
 * 
 * @author crossover
 *
 */
@RestController
public class RideController {

	
	@Autowired
	RideService rideService;
	
	@Autowired
	PersonService personService;

	@PostMapping(path = "/api/ride")
	public ResponseEntity<Ride> createNewRide(@RequestBody Ride ride) {

		//Test Start Date and End Date
		Date st = this.getDateFromString(ride.getStartTime());
		if( null == st )
			throw new RuntimeException("Invalid start date"); //Better exception should be made, Better return response code
		
		Date ed = this.getDateFromString(ride.getEndTime());
		if( null == ed )
			throw new RuntimeException("Invalid end date"); //Better exception should be made, Better return response code
		
		if( st.getTime() > ed.getTime() )
			throw new RuntimeException("start date cannot be greated than end date"); //Better exception should be made, Better return response code
		
		//Test Driver and rider
		if( null == ride.getDriver()  )
			throw new RuntimeException("driver not found"); //Better exception should be made, Better return response code
		
		if( null == ride.getRider()  )
			throw new RuntimeException("rider not found"); //Better exception should be made, Better return response code
		
		Person driver = personService.findById(ride.getDriver().getId());
		if( null == driver  )
			throw new RuntimeException("Invalid driver"); //Better exception should be made, Better return response code
		
		if( driver.getIsdriver() == false || driver.getRegistrationNumber() == null || driver.getRegistrationNumber().equals("") ){
			throw new RuntimeException("Invalid driver, driver not registered"); //Better exception should be made, Better return response code
		}
		
		Person rider = personService.findById(ride.getRider().getId());
		if( null == rider  )
			throw new RuntimeException("Invalid rider"); //Better exception should be made, Better return response code
		
		if( rider.getRegistrationNumber() == null || rider.getRegistrationNumber().equals("") ){
			throw new RuntimeException("Invalid rider, rider not registered"); //Better exception should be made, Better return response code
		}
		
		return ResponseEntity.ok(rideService.save(ride));
	}

	private Date getDateFromString(String dateStr) {
		
		Date st = null;
		try {
			final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			st = formatter.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return st;
	}

	@GetMapping(path = "/api/ride/{ride-id}")
	public ResponseEntity<Ride> getRideById(@PathVariable(name = "ride-id", required = true) Long rideId) {
		Ride ride = rideService.findById(rideId);
		if (ride != null)
			return ResponseEntity.ok(ride);
		return ResponseEntity.notFound().build();
	}

	/**
	 * This API returns the top 5 drivers with their email,name, total minutes,
	 * maximum ride duration in minutes. Only rides that starts and ends within
	 * the mentioned durations should be counted. Any rides where either start
	 * or endtime is outside the search, should not be considered.
	 * 
	 * DONT CHANGE METHOD SIGNATURE AND RETURN TYPES
	 * 
	 * @return
	 */
	@GetMapping(path = "/api/top-rides")
	public ResponseEntity<List<TopDriverDTO>> getTopDriver(@RequestParam(value = "max", defaultValue = "5") Long count,
			@RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
			@RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime) {
		List<TopDriverDTO> topDrivers = new ArrayList<TopDriverDTO>();
		/**
		 * Your Implementation Here. And Fill up topDrivers Arraylist with Top
		 * 
		 */

		return ResponseEntity.ok(topDrivers);

	}

}
