package my.flightdb.flightdbserver.controller;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.FlightDataResult;
import my.flightdb.flightdbserver.model.SearchResult;
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
        value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<Flight> getFlight(@PathVariable("id") Long id) {
        log.info("FlightRestController.getFlight called");

        Flight flight = flightService.findById(id);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value = "/{id}/flight_data",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<FlightDataResult> getFlightData(@PathVariable("id")Long id) {
        log.info("FlightRestController.getFlightData called");
        return getFlightDataDownSample(id, 1);
    }


    @RequestMapping(
            value = "/{id}/flight_data/{downsample}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<FlightDataResult> getFlightDataDownSample(
            @PathVariable("id")Long id,
            @PathVariable("downsample")int everyN) {
        log.info("FlightRestController.getFlightDataDownSample called");

        FlightDataResult result = flightDataService.findByFlightId(id, everyN);
        if (result.getRecords().size() > 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
