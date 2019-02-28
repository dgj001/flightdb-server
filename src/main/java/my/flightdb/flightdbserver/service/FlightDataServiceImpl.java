package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightDataServiceImpl implements FlightDataService {
    FlightDataRepository flightDataRepository;

    public FlightDataServiceImpl(FlightDataRepository flightDataRepository) {
        this.flightDataRepository = flightDataRepository;
    }

    @Override
    public Iterable<FlightData> findByFlightId(Long flightId) {
        return flightDataRepository.findByFlightId(flightId);
    }

    @Override
    public FlightData save(FlightData object) {
        return flightDataRepository.save(object);
    }
}
