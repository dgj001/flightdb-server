package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;

public interface FlightDataService {
    Iterable<FlightData> findByFlightId(Long flightId);
    FlightData save(FlightData object);
}
