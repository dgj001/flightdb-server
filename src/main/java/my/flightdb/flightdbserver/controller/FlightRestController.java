package my.flightdb.flightdbserver.controller;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.Grouping;
import my.flightdb.flightdbserver.model.SearchResult;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import my.flightdb.flightdbserver.service.FlightDataService;
import my.flightdb.flightdbserver.service.FlightService;
import my.flightdb.flightdbserver.service.FlightPredicate;
import my.flightdb.flightdbserver.service.GroupingService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
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

    FlightDataService flightDataService;

    GroupingService groupingService;

    public FlightRestController(FlightService flightService, FlightDataService flightDataService, GroupingService groupingService) {
        this.flightService = flightService;
        this.flightDataService = flightDataService;
        this.groupingService = groupingService;
    }

    @RequestMapping(
        value="/search",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<SearchResult> search(@RequestParam MultiValueMap<String, String> allRequestParams, Pageable pageable) {
        log.info("FlightRestController.search called");

        Predicate predicate = FlightPredicate.build(allRequestParams);
        Long count = flightService.count(predicate);
        if (count > 0) {
            SearchResult result = new SearchResult();
            result.setCount(count);
            result.setFlights(flightService.search(predicate, pageable));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
        value = "/tail_numbers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
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

    @RequestMapping(
            value = "/departure_airports",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Collection<String>> departureAirports() {
        log.info("FlightRestController.departureAirports called");

        List<String> airports = flightService.findDistinctDepartureAirports();
        if (airports.size() > 0) {
            return new ResponseEntity<>(airports, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/arrival_airports",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Collection<String>> arrivalAirports() {
        log.info("FlightRestController.arrivalAirports called");

        List<String> airports = flightService.findDistinctArrivalAirports();
        if (airports.size() > 0) {
            return new ResponseEntity<>(airports, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Flight> getFlightWithData(@PathVariable("id")Long id) {
        log.info("FlightRestController.getFlightWithData called");

        Flight flight = flightService.findById(id);
        if (flight != null) {
            log.info("flight.tailNumber: " + flight.getTailNumber());

            flight.setRecords(flightDataService.findByFlightId(id));
            log.info("records.size: " + flight.getRecords().size());

            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
