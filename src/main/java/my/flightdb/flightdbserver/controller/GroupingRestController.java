package my.flightdb.flightdbserver.controller;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Grouping;
import my.flightdb.flightdbserver.model.SearchResult;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import my.flightdb.flightdbserver.service.FlightPredicate;
import my.flightdb.flightdbserver.service.FlightService;
import my.flightdb.flightdbserver.service.GroupingService;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("api/groupings")
public class GroupingRestController {

    GroupingService groupingService;

    public GroupingRestController(FlightService flightService, FlightDataRepository flightDataRepository, GroupingService groupingService) {
        this.groupingService = groupingService;
    }

    @RequestMapping(
            value = "/departure_airports",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Collection<Grouping>> departureAirports(Pageable pageable) {
        log.info("GroupingRestController.departureAirports called");

        List<Grouping> groupings = groupingService.findAll("departure_airport", pageable);
        if (groupings.size() > 0) {
            return new ResponseEntity<>(groupings, HttpStatus.OK);
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
    public ResponseEntity<Collection<Grouping>> arrivalAirports(Pageable pageable) {
        log.info("GroupingRestController.arrivalAirports called");

        List<Grouping> groupings = groupingService.findAll("arrival_airport", pageable);
        if (groupings.size() > 0) {
            return new ResponseEntity<>(groupings, HttpStatus.OK);
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
    public ResponseEntity<Collection<Grouping>> tailNumbers(Pageable pageable) {
        log.info("GroupingRestController.tailNumbers called");

        List<Grouping> groupings = groupingService.findAll("tail_number", pageable);
        if (groupings.size() > 0) {
            return new ResponseEntity<>(groupings, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
