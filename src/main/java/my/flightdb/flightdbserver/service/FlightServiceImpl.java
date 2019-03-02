package my.flightdb.flightdbserver.service;

import com.querydsl.core.BooleanBuilder;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final String TAIL1 = "XYZ01";
    private final String TYPE1 = "Boeing 737";
    private final LocalDateTime DATE1 = LocalDateTime.of(2007, 12, 31, 23, 59, 59);

    private final String TAIL2 = "XYZ02";
    private final String TYPE2 = "Airbus 319";
    private final LocalDateTime DATE2 = LocalDateTime.of(2016, 2, 29, 0, 0, 0);

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Long count() {
        return this.flightRepository.count();
    }

    @Override
    public Iterable<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight save(Flight object) {
        return flightRepository.save(object);
    }

    @Override
    public List<Flight> search(MultiValueMap<String, String> params) {
        BooleanBuilder searchBuilder = new BooleanBuilder();
        List<Flight> flights = new ArrayList<>();
        flightRepository.findAll(searchBuilder).forEach(flights::add);
        return flights;
    }
}
