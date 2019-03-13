package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.repository.FlightRepository;
import org.springframework.data.domain.Pageable;
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
    public List<Flight> search(Predicate predicate, Pageable pageable) {
        List<Flight> flights = new ArrayList<>();
        flightRepository.findAll(predicate, pageable).forEach(flights::add);
        return flights;
    }

    @Override
    public List<String> findDistinctAircraftTypes() {
        return flightRepository.findDistinctAircraftTypes();
    }

    @Override
    public List<String> findDistinctTailNumbers() {
        return flightRepository.findDistinctTailNumbers();
    }

    @Override
    public List<String> findDistinctDepartureAirports() {
        return flightRepository.findDistinctDepartureAirports();
    }

    @Override
    public List<String> findDistinctArrivalAirports() {
        return flightRepository.findDistinctArrivalAirports();
    }

}
