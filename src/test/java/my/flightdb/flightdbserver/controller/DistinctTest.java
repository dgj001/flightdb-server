package my.flightdb.flightdbserver.controller;

import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.Grouping;
import my.flightdb.flightdbserver.service.FlightService;
import my.flightdb.flightdbserver.service.GroupingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class DistinctTest {
    @Autowired
    FlightRestController controller;

    @Autowired
    FlightService flightService;

    private void loadFlightTable() {
        Flight flight;

        flight = Flight.builder().tailNumber("tail1").departureAirport("KORD").arrivalAirport("K123").build();
        flightService.save(flight);

        flight = Flight.builder().tailNumber("tail2").departureAirport("KORD").arrivalAirport("K123").build();
        flightService.save(flight);

        flight = Flight.builder().tailNumber("tail2").departureAirport("KORD").arrivalAirport("").build();
        flightService.save(flight);

        flight = Flight.builder().tailNumber("tail3").departureAirport("KCMH").arrivalAirport("").build();
        flightService.save(flight);
    }

    @Before
    public void loadDistinctTestDB() {
        log.info("loadDistinctTestDB");
        loadFlightTable();
    }

    @Test
    public void shouldReturnDistinctTails() {
        Collection<String> tails = controller.tailNumbers().getBody();
        assertEquals(3, tails.size());
        Iterator<String> it = tails.iterator();
        assertEquals("tail1", it.next());
        assertEquals("tail2", it.next());
        assertEquals("tail3", it.next());
    }

    @Test
    public void shouldReturnDistinctDepartures() {
        Collection<String> airports = controller.departureAirports().getBody();
        assertEquals(2, airports.size());
        Iterator<String> it = airports.iterator();
        assertEquals("KCMH", it.next());
        assertEquals("KORD", it.next());
    }

    @Test
    public void shouldReturnDistinctArrivals() {
        Collection<String> airports = controller.arrivalAirports().getBody();
        assertEquals(2, airports.size());
        Iterator<String> it = airports.iterator();
        assertEquals("K123", it.next());
        assertEquals("", it.next());
    }
}
