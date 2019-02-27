package my.flightdb.flightdbserver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightRepositoryTest {

    @Autowired
    FlightService flightService;

    @Test
    public void count() {
        Long actual = flightService.count();
        assertEquals(Long.valueOf(2L), actual);
    }
}
