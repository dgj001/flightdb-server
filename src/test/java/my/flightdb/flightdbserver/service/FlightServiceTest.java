package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Flight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceTest {

    @Autowired
    FlightService flightService;

    @Before
    public void setUp() throws Exception {
        Flight f1 = new Flight();
        f1.setTailNumber("XYZ01");
        flightService.save(f1);

        Flight f2 = new Flight();
        f2.setTailNumber("XYZ02");
        flightService.save(f2);
    }

    @Test
    public void count() {
        Long actual = flightService.count();
        assertEquals(Long.valueOf(2L), actual);
    }
}
