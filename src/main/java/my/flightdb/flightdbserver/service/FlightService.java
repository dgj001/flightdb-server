package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
import my.flightdb.flightdbserver.model.Flight;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {
    Long count();
    Long count(Predicate predicate);
    List<Flight> findAll();
    Flight save(Flight object);
    List<Flight> search(Predicate predicate, Pageable request);
    List<String> findDistinctAircraftTypes();
    List<String> findDistinctTailNumbers();
    List<String> findDistinctDepartureAirports();
    List<String> findDistinctArrivalAirports();
}
