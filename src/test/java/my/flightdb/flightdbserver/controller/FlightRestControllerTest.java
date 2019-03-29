package my.flightdb.flightdbserver.controller;

import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.FlightDataResult;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FlightRestControllerTest {
    private final double EPSILON = 0.0001;

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
    public void flightShouldWork() {
        ResponseEntity<Flight> response = controller.getFlight(TestDB.flightId1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Flight flight = response.getBody();

        assertEquals(TestDB.TAIL1, flight.getTailNumber());
        assertEquals(TestDB.ARR1, flight.getArrivalAirport());
        assertEquals(TestDB.DEP1, flight.getDepartureAirport());

    }

    @Test
    public void flightDataShouldWork() {
        ResponseEntity<FlightDataResult> response = controller.getFlightData(0L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        response = controller.getFlightData(TestDB.flightId1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        FlightDataResult result = response.getBody();

        assertEquals(2, result.getRecords().size());
        FlightData flightData = result.getRecords().get(0);
        assertEquals(TestDB.DATA_1_ALT_1, flightData.getAltitude(), EPSILON);
        assertEquals(TestDB.DATA_1_GS_1, flightData.getGroundSpeed(), EPSILON);
        assertEquals(TestDB.DATA_1_LAT_1, flightData.getLatitude(), EPSILON);
        assertEquals(TestDB.DATA_1_LNG_1, flightData.getLongitude(), EPSILON);
        assertEquals(TestDB.DATA_1_TIME_1, flightData.getTime(), EPSILON);

        flightData = result.getRecords().get(1);
        assertEquals(TestDB.DATA_1_ALT_2, flightData.getAltitude(), EPSILON);
        assertEquals(TestDB.DATA_1_GS_2, flightData.getGroundSpeed(), EPSILON);
        assertEquals(TestDB.DATA_1_LAT_2, flightData.getLatitude(), EPSILON);
        assertEquals(TestDB.DATA_1_LNG_2, flightData.getLongitude(), EPSILON);
        assertEquals(TestDB.DATA_1_TIME_2, flightData.getTime(), EPSILON);

        assertEquals(result.getMinLat(), 3.0, EPSILON);
        assertEquals(result.getMaxLat(), 13.0, EPSILON);
        assertEquals(result.getMinLng(), 4.0, EPSILON);
        assertEquals(result.getMaxLng(), 14.0, EPSILON);

        response = controller.getFlightData(TestDB.flightId2);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        result = response.getBody();

        assertEquals(1, result.getRecords().size());
        flightData = result.getRecords().get(0);
        assertEquals(TestDB.DATA_2_ALT_1, flightData.getAltitude(), EPSILON);
        assertEquals(TestDB.DATA_2_GS_1, flightData.getGroundSpeed(), EPSILON);
        assertEquals(TestDB.DATA_2_LAT_1, flightData.getLatitude(), EPSILON);
        assertEquals(TestDB.DATA_2_LNG_1, flightData.getLongitude(), EPSILON);
        assertEquals(TestDB.DATA_2_TIME_1, flightData.getTime(), EPSILON);

        assertEquals(result.getMinLat(), 23.0, EPSILON);
        assertEquals(result.getMaxLat(), 23.0, EPSILON);
        assertEquals(result.getMinLng(), 24.0, EPSILON);
        assertEquals(result.getMaxLng(), 24.0, EPSILON);
    }
}
