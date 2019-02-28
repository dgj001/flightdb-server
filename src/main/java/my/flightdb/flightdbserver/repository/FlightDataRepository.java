package my.flightdb.flightdbserver.repository;

import my.flightdb.flightdbserver.model.FlightData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FlightDataRepository extends CrudRepository<FlightData, Long> {
    @Query("SELECT fd FROM FlightData fd WHERE fd.flightId = :flightId")
    Iterable<FlightData> findByFlightId(@Param("flightId") Long flightId);
}
