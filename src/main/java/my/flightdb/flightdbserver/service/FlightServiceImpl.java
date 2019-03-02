package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Long count() {
        return flightRepository.count();
    }

    @Override
    public Long count(Predicate predicate) {
        return flightRepository.count(predicate);
    }

    @Override
    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        flightRepository.findAll().forEach(flights::add);
        return flights;
    }

    @Override
    public Flight save(Flight object) {
        return flightRepository.save(object);
    }

    @Override
    public List<Flight> search(Predicate predicate) {
        List<Flight> flights = new ArrayList<>();
        flightRepository.findAll(predicate).forEach(flights::add);
        return flights;
    }
}
