package my.flightdb.flightdbserver.controller;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.SearchResult;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import my.flightdb.flightdbserver.service.FlightService;
import my.flightdb.flightdbserver.service.PredicateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
public class FlightRestController {

    FlightService flightService;

    FlightDataRepository flightDataRepository;

    PredicateBuilder predicateBuilder = new PredicateBuilder();

    public FlightRestController(FlightService flightService, FlightDataRepository flightDataRepository) {
        this.flightService = flightService;
        this.flightDataRepository = flightDataRepository;
    }

    public ResponseEntity<SearchResult> search(@RequestParam MultiValueMap<String, String> allRequestParams) {
        log.info("FlightRestController.search called");

        Predicate predicate = predicateBuilder.buildFlightPredicate(allRequestParams);
        Long count = flightService.count(predicate);
        if (count > 0) {
            SearchResult result = new SearchResult();
            result.setCount(count);
            result.setFlights(flightService.search(predicate));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
