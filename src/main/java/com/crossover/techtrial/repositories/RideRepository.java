/**
 * 
 */
package com.crossover.techtrial.repositories;

import com.crossover.techtrial.model.Ride;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author crossover
 *
 */
@RestResource(exported = false)
public interface RideRepository extends CrudRepository<Ride, Long> {

	 @Query("SELECT r.id, sum(distance), "
	 		+ "max(TIMESTAMPDIFF(SECOND,  STR_TO_DATE(r.startTime,'%Y-%m-%dT%h:%m:%s'),  STR_TO_DATE(r.endTime,'%Y-%m-%dT%h:%m:%s'))), "
	 		+ "avg(TIMESTAMPDIFF(SECOND,  STR_TO_DATE(r.startTime,'%Y-%m-%dT%h:%m:%s'),  STR_TO_DATE(r.endTime,'%Y-%m-%dT%h:%m:%s'))), "
	 		+ "  FROM Ride r where  "
	 		+ "	STR_TO_DATE(r.startTime,'%Y-%m-%dT%h:%m:%s') between"
	 		+ " 	STR_TO_DATE(:startDate,'%Y-%m-%dT%h:%m:%s') and  STR_TO_DATE(:endDate,'%Y-%m-%dT%h:%m:%s') order by sum(distance) desc limit :count") 
	 Optional<List> findTopDriver(@Param("count") int count, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
