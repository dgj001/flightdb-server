package my.flightdb.flightdbserver.controller;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.SearchResult;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import my.flightdb.flightdbserver.service.FlightService;
import my.flightdb.flightdbserver.service.FlightPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("api/flights")
public class FlightRestController {

    FlightService flightService;

    FlightDataRepository flightDataRepository;

    public FlightRestController(FlightService flightService, FlightDataRepository flightDataRepository) {
        this.flightService = flightService;
        this.flightDataRepository = flightDataRepository;
    }

    @RequestMapping(value="/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SearchResult> search(@RequestParam MultiValueMap<String, String> allRequestParams) {
        log.info("FlightRestController.search called");

        Predicate predicate = FlightPredicate.build(allRequestParams);
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

    @RequestMapping(value = "/aircraft_types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<String>> aircraftTypes() {
        log.info("FlightRestController.aircraftTypes called");

        List<String> types = flightService.findDistinctAircraftTypes();
        if (types.size() > 0) {
            return new ResponseEntity<>(types, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/tail_numbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<String>> tailNumbers() {
        log.info("FlightRestController.tailNumbers called");

        List<String> tailNos = flightService.findDistinctTailNumbers();
        if (tailNos.size() > 0) {
            return new ResponseEntity<>(tailNos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
