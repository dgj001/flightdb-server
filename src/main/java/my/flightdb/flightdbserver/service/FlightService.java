package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;

public interface FlightService {
    Long count();
    Flight save(Flight object);
}
