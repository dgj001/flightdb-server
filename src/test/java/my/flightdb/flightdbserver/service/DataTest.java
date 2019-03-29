package my.flightdb.flightdbserver.service;

import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.FlightDataResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class DataTest {
    private final double EPSILON = 0.0001;

    @Autowired
    FlightService flightService;

    @Autowired
    FlightDataService flightDataService;

    @Autowired
    TestDB testDB;

    @Before
    public void createTestDB() {
        testDB.createSharedDB();
    }

    @Test
    public void countShouldBeValid() {
        Long actual = flightService.count();
        assertEquals(Long.valueOf(3L), actual);
    }

    @Test
    public void flightFieldsShouldBeValid() {
        Iterator<Flight> iterator = flightService.findAll().iterator();

        Flight flight1 = iterator.next();
        assertEquals(TestDB.TAIL1, flight1.getTailNumber());
        assertEquals(TestDB.DEP1, flight1.getDepartureAirport());
        assertEquals(TestDB.ARR1, flight1.getArrivalAirport());
        assertEquals(TestDB.TYPE1, flight1.getAircraftType());
        assertEquals(TestDB.DATE1, flight1.getStartDateTime());

        Flight flight2 = iterator.next();
        assertEquals(TestDB.TAIL2, flight2.getTailNumber());
        assertEquals(TestDB.DEP2, flight2.getDepartureAirport());
        assertEquals(TestDB.ARR2, flight2.getArrivalAirport());
        assertEquals(TestDB.TYPE2, flight2.getAircraftType());
        assertEquals(TestDB.DATE2, flight2.getStartDateTime());
    }

    @Test
    public void flightDataFieldsShouldBeValid() {
        FlightDataResult result = flightDataService.findByFlightId(TestDB.flightId1, 1);

        assertEquals(2, result.getRecords().size());

        FlightData data;

        data = result.getRecords().get(0);
        assertEquals(TestDB.flightId1, data.getFlightId());
        assertEquals(TestDB.DATA_1_TIME_1, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_1_ALT_1, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_1_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_1_LAT_1, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_1_LNG_1, data.getLongitude(), 0.001);

        data = result.getRecords().get(1);
        assertEquals(TestDB.DATA_1_TIME_2, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_1_ALT_2, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_1_GS_2, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_1_LAT_2, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_1_LNG_2, data.getLongitude(), 0.001);

        result = flightDataService.findByFlightId(TestDB.flightId2, 1);

        data = result.getRecords().get(0);
        assertEquals(TestDB.flightId2, data.getFlightId());
        assertEquals(TestDB.DATA_2_TIME_1, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_2_ALT_1, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_2_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_2_LAT_1, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_2_LNG_1, data.getLongitude(), 0.001);
    }

    @Test
    public void flightDataDownsampleShouldWork() {
        FlightDataResult result = flightDataService.findByFlightId(TestDB.flightId3, 5);

        assertEquals(3, result.getNumRecords());
        assertEquals(3, result.getRecords().size());

        assertEquals(TestDB.DATA_3_LAT_1, result.getRecords().get(0).getLatitude(), EPSILON);
        assertEquals(TestDB.DATA_3_LAT_6, result.getRecords().get(1).getLatitude(), EPSILON);
        assertEquals(TestDB.DATA_3_LAT_11, result.getRecords().get(2).getLatitude(), EPSILON);
   }
}
