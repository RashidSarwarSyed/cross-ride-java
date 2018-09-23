/**
 * 
 */
package com.crossover.techtrial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author crossover
 *
 */
@Service
public class RideServiceImpl implements RideService{

  @Autowired
  RideRepository rideRepository;
  
  @Autowired
  PersonRepository personRepository;
  
  public Ride save(Ride ride) {
    return rideRepository.save(ride);
  }
  
  public Ride findById(Long rideId) {
    Optional<Ride> optionalRide = rideRepository.findById(rideId);
    if (optionalRide.isPresent()) {
      return optionalRide.get();
    }else return null;
  }
  
  public List<TopDriverDTO> findTopRider(int count, String startDate, String endDate){
	  Optional<List> valslst = rideRepository.findTopDriver(count, startDate, endDate);
	  List<TopDriverDTO> lstRide = new ArrayList<>();
	  
	  if( valslst.isPresent() == false )
		  return null;
	  
	  valslst.get().stream().forEach(rd -> {
		  Object[] vals = (Object[])rd;
		  Person p = personRepository.findById(this.isLongReturn0(vals[0])).get();
		  
		  TopDriverDTO td = new TopDriverDTO();
		  td.setName(p.getName());
		  td.setEmail(p.getEmail());
		  td.setTotalRideDurationInSeconds(this.isLongReturn0(vals[1]));
		  td.setMaxRideDurationInSecods(this.isLongReturn0(vals[2]));
		  td.setAverageDistance(this.isDoubleReturn0(vals[0]));
	  });
	  
	  return lstRide;
  }
  
  private Long isLongReturn0(Object val){
	  if( null == val) return 0l;
	  try{ return Long.parseLong(val.toString()); } catch(NumberFormatException e) {}
	  return 0l;
  }
  
  private Double isDoubleReturn0(Object val){
	  if( null == val) return 0d;
	  try{ return Double.parseDouble(val.toString()); } catch(NumberFormatException e) {}
	  return 0d;
  }

}
