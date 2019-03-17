package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightDataServiceImpl implements FlightDataService {
    FlightDataRepository flightDataRepository;

    public FlightDataServiceImpl(FlightDataRepository flightDataRepository) {
        this.flightDataRepository = flightDataRepository;
    }

    @Override
    public List<FlightData> findByFlightId(Long flightId) {
        List<FlightData> records = new ArrayList<>();
        flightDataRepository.findByFlightId(flightId).forEach(records::add);
        return records;
    }

    @Override
    public FlightData save(FlightData object) {
        return flightDataRepository.save(object);
    }
}
