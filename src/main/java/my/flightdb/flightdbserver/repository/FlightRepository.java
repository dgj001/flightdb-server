package my.flightdb.flightdbserver.repository;

import my.flightdb.flightdbserver.model.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {
}
