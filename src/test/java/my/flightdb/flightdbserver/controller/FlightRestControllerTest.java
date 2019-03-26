package my.flightdb.flightdbserver.controller;

import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.SearchResult;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FlightRestControllerTest {

    @Autowired
    TestDB testDB;

    @Autowired
    FlightRestController controller;

    @Before
    public void createTestDB() {
        testDB.createSharedDB();
    }

    @Test
    public void shouldReturnSearchResult() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        ResponseEntity<SearchResult> response = controller.search(params, PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SearchResult result = response.getBody();
        assertEquals(3L, (long) result.getCount());
        assertEquals(3L, result.getFlights().size());
    }

    @Test
    public void shouldReturnEmptyResponse() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("tailNumber", "non-existent type");
        ResponseEntity<SearchResult> response = controller.search(params, PageRequest.of(0, 20));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        SearchResult result = response.getBody();
        System.out.println("dummy");
    }

    @Test
    public void tailNumbersShouldWork() {
        ResponseEntity<Collection<String>> response = controller.tailNumbers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Collection<String> tailNos = response.getBody();
        assertEquals(3, tailNos.size());
        assertTrue(tailNos.contains(TestDB.TAIL1));
        assertTrue(tailNos.contains(TestDB.TAIL2));
        assertTrue(tailNos.contains(TestDB.TAIL3));
    }

    @Test
    public void flightWithoutDataShouldWork() {
        ResponseEntity<Flight> response = controller.getFlightWithoutData(TestDB.flightId1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Flight flight = response.getBody();

        assertEquals(TestDB.TAIL1, flight.getTailNumber());
        assertEquals(TestDB.ARR1, flight.getArrivalAirport());
        assertEquals(TestDB.DEP1, flight.getDepartureAirport());

    }

    @Test
    public void flightWithDataShouldWork() {
        ResponseEntity<Flight> response = controller.getFlightWithData(0L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        response = controller.getFlightWithData(TestDB.flightId1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Flight flight = response.getBody();

        assertEquals(TestDB.TAIL1, flight.getTailNumber());
        assertEquals(2, flight.getRecords().size());
        FlightData flightData = flight.getRecords().get(0);
        assertEquals(TestDB.DATA_1_ALT_1, flightData.getAltitude(), 0.0001);
        assertEquals(TestDB.DATA_1_GS_1, flightData.getGroundSpeed(), 0.0001);
        assertEquals(TestDB.DATA_1_LAT_1, flightData.getLatitude(), 0.0001);
        assertEquals(TestDB.DATA_1_LNG_1, flightData.getLongitude(), 0.0001);
        assertEquals(TestDB.DATA_1_TIME_1, flightData.getTime(), 0.0001);

        flightData = flight.getRecords().get(1);
        assertEquals(TestDB.DATA_1_ALT_2, flightData.getAltitude(), 0.0001);
        assertEquals(TestDB.DATA_1_GS_2, flightData.getGroundSpeed(), 0.0001);
        assertEquals(TestDB.DATA_1_LAT_2, flightData.getLatitude(), 0.0001);
        assertEquals(TestDB.DATA_1_LNG_2, flightData.getLongitude(), 0.0001);
        assertEquals(TestDB.DATA_1_TIME_2, flightData.getTime(), 0.0001);

        response = controller.getFlightWithData(TestDB.flightId2);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        flight = response.getBody();

        assertEquals(TestDB.TAIL2, flight.getTailNumber());
        assertEquals(1, flight.getRecords().size());
        flightData = flight.getRecords().get(0);
        assertEquals(TestDB.DATA_2_ALT_1, flightData.getAltitude(), 0.0001);
        assertEquals(TestDB.DATA_2_GS_1, flightData.getGroundSpeed(), 0.0001);
        assertEquals(TestDB.DATA_2_LAT_1, flightData.getLatitude(), 0.0001);
        assertEquals(TestDB.DATA_2_LNG_1, flightData.getLongitude(), 0.0001);
        assertEquals(TestDB.DATA_2_TIME_1, flightData.getTime(), 0.0001);
    }
}
