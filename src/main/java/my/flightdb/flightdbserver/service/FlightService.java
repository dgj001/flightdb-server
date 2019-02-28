package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;

public interface FlightService {
    Iterable<Flight> findAll();
    Long count();
    Flight save(Flight object);
}
