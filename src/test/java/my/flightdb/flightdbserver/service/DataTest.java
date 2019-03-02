package my.flightdb.flightdbserver.service;

import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DataTest {

    @Autowired
    FlightService flightService;

    @Autowired
    FlightDataService flightDataService;

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
        assertEquals(TestDB.TYPE1, flight1.getAircraftType());
        assertEquals(TestDB.DATE1, flight1.getStartDateTime());

        Flight flight2 = iterator.next();
        assertEquals(TestDB.TAIL2, flight2.getTailNumber());
        assertEquals(TestDB.TYPE2, flight2.getAircraftType());
        assertEquals(TestDB.DATE2, flight2.getStartDateTime());
    }

    @Test
    public void flightDataFieldsShouldBeValid() {
        Iterator<FlightData> iterator = flightDataService.findByFlightId(TestDB.flightId1).iterator();

        FlightData data;

        data = iterator.next();
        assertEquals(TestDB.flightId1, data.getFlightId());
        assertEquals(TestDB.DATA_1_TIME_1, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_1_ALT_1, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_1_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_1_LAT_1, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_1_LNG_1, data.getLongitude(), 0.001);

        data = iterator.next();
        assertEquals(TestDB.DATA_1_TIME_2, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_1_ALT_2, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_1_GS_2, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_1_LAT_2, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_1_LNG_2, data.getLongitude(), 0.001);

        iterator = flightDataService.findByFlightId(TestDB.flightId2).iterator();

        data = iterator.next();
        assertEquals(TestDB.flightId2, data.getFlightId());
        assertEquals(TestDB.DATA_2_TIME_1, data.getTime(), 0.001);
        assertEquals(TestDB.DATA_2_ALT_1, data.getAltitude(), 0.001);
        assertEquals(TestDB.DATA_2_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(TestDB.DATA_2_LAT_1, data.getLatitude(), 0.001);
        assertEquals(TestDB.DATA_2_LNG_1, data.getLongitude(), 0.001);
    }
}
