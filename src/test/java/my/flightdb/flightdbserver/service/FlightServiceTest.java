package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceTest {

    @Autowired
    FlightService flightService;

    @Autowired
    FlightDataService flightDataService;

    private final String TAIL0 = "XYZ01";
    private final String TYPE1 = "Boeing 737";
    private final LocalDateTime DATE1 = LocalDateTime.of(2007, 12, 31, 23, 59, 59);

    private final String TAIL2 = "XYZ02";
    private final String TYPE2 = "Airbus 319";
    private final LocalDateTime DATE2 = LocalDateTime.of(2016, 2, 29, 0, 0, 0);

    private Long flightId1;
    private final double DATA_1_TIME_1 = 0.0;
    private final double DATA_1_ALT_1 = 1.0;
    private final double DATA_1_GS_1 = 2.0;
    private final double DATA_1_LAT_1 = 3.0;
    private final double DATA_1_LNG_1 = 4.0;
    private final double DATA_1_TIME_2 = 10.0;
    private final double DATA_1_ALT_2 = 11.0;
    private final double DATA_1_GS_2 = 12.0;
    private final double DATA_1_LAT_2 = 13.0;
    private final double DATA_1_LNG_2 = 14.0;

    private Long flightId2;
    private final double DATA_2_TIME_1 = 20.0;
    private final double DATA_2_ALT_1 = 21.0;
    private final double DATA_2_GS_1 = 22.0;
    private final double DATA_2_LAT_1 = 23.0;
    private final double DATA_2_LNG_1 = 24.0;

    @Before
    public void setUp() {
        Flight f1 = new Flight();
        f1.setTailNumber(TAIL0);
        f1.setAircraftType(TYPE1);
        f1.setStartDateTime(DATE1);
        flightService.save(f1);

        flightId1 = f1.getId();

        FlightData data;

        data = new FlightData();
        data.setFlightId(flightId1);
        data.setTime(DATA_1_TIME_1);
        data.setAltitude(DATA_1_ALT_1);
        data.setGroundSpeed(DATA_1_GS_1);
        data.setLatitude(DATA_1_LAT_1);
        data.setLongitude(DATA_1_LNG_1);
        flightDataService.save(data);

        data = new FlightData();
        data.setFlightId(flightId1);
        data.setTime(DATA_1_TIME_2);
        data.setAltitude(DATA_1_ALT_2);
        data.setGroundSpeed(DATA_1_GS_2);
        data.setLatitude(DATA_1_LAT_2);
        data.setLongitude(DATA_1_LNG_2);
        flightDataService.save(data);

        Flight f2 = new Flight();
        f2.setTailNumber(TAIL2);
        f2.setAircraftType(TYPE2);
        f2.setStartDateTime(DATE2);
        flightService.save(f2);

        flightId2 = f2.getId();

        data = new FlightData();
        data.setFlightId(flightId2);
        data.setTime(DATA_2_TIME_1);
        data.setAltitude(DATA_2_ALT_1);
        data.setGroundSpeed(DATA_2_GS_1);
        data.setLatitude(DATA_2_LAT_1);
        data.setLongitude(DATA_2_LNG_1);
        flightDataService.save(data);
    }

    @Test
    public void flightCount() {
        Long actual = flightService.count();
        assertEquals(Long.valueOf(2L), actual);
    }

    @Test
    public void flightFields() {
        Iterator<Flight> iterator = flightService.findAll().iterator();

        Flight flight1 = iterator.next();
        assertEquals(TAIL0, flight1.getTailNumber());
        assertEquals(TYPE1, flight1.getAircraftType());
        assertEquals(DATE1, flight1.getStartDateTime());

        Flight flight2 = iterator.next();
        assertEquals(TAIL2, flight2.getTailNumber());
        assertEquals(TYPE2, flight2.getAircraftType());
        assertEquals(DATE2, flight2.getStartDateTime());
    }

    @Test
    public void flightDataFields() {
        Iterator<FlightData> iterator = flightDataService.findByFlightId(flightId1).iterator();

        FlightData data;

        data = iterator.next();
        assertEquals(flightId1, data.getFlightId());
        assertEquals(DATA_1_TIME_1, data.getTime(), 0.001);
        assertEquals(DATA_1_ALT_1, data.getAltitude(), 0.001);
        assertEquals(DATA_1_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(DATA_1_LAT_1, data.getLatitude(), 0.001);
        assertEquals(DATA_1_LNG_1, data.getLongitude(), 0.001);

        data = iterator.next();
        assertEquals(DATA_1_TIME_2, data.getTime(), 0.001);
        assertEquals(DATA_1_ALT_2, data.getAltitude(), 0.001);
        assertEquals(DATA_1_GS_2, data.getGroundSpeed(), 0.001);
        assertEquals(DATA_1_LAT_2, data.getLatitude(), 0.001);
        assertEquals(DATA_1_LNG_2, data.getLongitude(), 0.001);

        iterator = flightDataService.findByFlightId(flightId2).iterator();
        
        data = iterator.next();
        assertEquals(flightId2, data.getFlightId());
        assertEquals(DATA_2_TIME_1, data.getTime(), 0.001);
        assertEquals(DATA_2_ALT_1, data.getAltitude(), 0.001);
        assertEquals(DATA_2_GS_1, data.getGroundSpeed(), 0.001);
        assertEquals(DATA_2_LAT_1, data.getLatitude(), 0.001);
        assertEquals(DATA_2_LNG_1, data.getLongitude(), 0.001);
    }
}
