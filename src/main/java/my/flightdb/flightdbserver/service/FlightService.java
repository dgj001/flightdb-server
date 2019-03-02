package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface FlightService {
    Long count();
    Iterable<Flight> findAll();
    Flight save(Flight object);
    List<Flight> search(MultiValueMap<String, String> params);
}
