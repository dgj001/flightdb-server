package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;
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

    private final String TAIL1 = "XYZ01";
    private final String TYPE1 = "Boeing 737";
    private final LocalDateTime DATE1 = LocalDateTime.of(2007, 12, 31, 23, 59, 59);

    private final String TAIL2 = "XYZ02";
    private final String TYPE2 = "Airbus 319";
    private final LocalDateTime DATE2 = LocalDateTime.of(2016, 2, 29, 0, 0, 0);

    @Before
    public void setUp() {
        Flight f1 = new Flight();
        f1.setTailNumber(TAIL1);
        f1.setAircraftType(TYPE1);
        f1.setStartDateTime(DATE1);
        flightService.save(f1);

        Flight f2 = new Flight();
        f2.setTailNumber(TAIL2);
        f2.setAircraftType(TYPE2);
        f2.setStartDateTime(DATE2);
        flightService.save(f2);
    }

    @Test
    public void count() {
        Long actual = flightService.count();
        assertEquals(Long.valueOf(2L), actual);
    }

    @Test
    public void fields() {
        Iterator<Flight> iterator = flightService.findAll().iterator();

        Flight flight1 = iterator.next();
        assertEquals(TAIL1, flight1.getTailNumber());
        assertEquals(TYPE1, flight1.getAircraftType());
        assertEquals(DATE1, flight1.getStartDateTime());

        Flight flight2 = iterator.next();
        assertEquals(TAIL2, flight2.getTailNumber());
        assertEquals(TYPE2, flight2.getAircraftType());
        assertEquals(DATE2, flight2.getStartDateTime());
    }
}
