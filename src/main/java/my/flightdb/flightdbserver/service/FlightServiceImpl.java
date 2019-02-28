package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.repository.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Long count() {
        return this.flightRepository.count();
    }

    @Override
    public Flight save(Flight object) {
        return flightRepository.save(object);
    }
}
