package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
import my.flightdb.flightdbserver.model.Flight;

import java.util.List;

public interface FlightService {
    Long count();
    Long count(Predicate predicate);
    List<Flight> findAll();
    Flight save(Flight object);
    List<Flight> search(Predicate predicate);
}
