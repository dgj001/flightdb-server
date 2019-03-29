package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.FlightDataResult;

public interface FlightDataService {
    FlightDataResult findByFlightId(Long flightId, int everyN);
    FlightData save(FlightData object);
}
