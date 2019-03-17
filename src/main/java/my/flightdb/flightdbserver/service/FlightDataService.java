package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;

import java.util.List;

public interface FlightDataService {
    List<FlightData> findByFlightId(Long flightId);
    FlightData save(FlightData object);
}
