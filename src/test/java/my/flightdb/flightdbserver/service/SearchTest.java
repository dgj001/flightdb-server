package my.flightdb.flightdbserver.service;

import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Flight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SearchTest {
    @Autowired
    FlightService flightService;

    final String F1_TYPE = "Cessna 172";

    final String F2_TYPE = "15 Number first";

    final String F3_TYPE = ""; // empty

    @Before
    public void initializeTestRecords() {
        if (flightService.count() > 0) {
            return;
        }

        Flight flight;

        flight = new Flight();
        flight.setAircraftType(F1_TYPE);
        flightService.save(flight);

        flight = new Flight();
        flight.setAircraftType(F2_TYPE);
        flightService.save(flight);

        flight = new Flight();
        flight.setAircraftType(F3_TYPE);
        flightService.save(flight);
    }

    @Test
    public void searchWithoutParamsShouldGetAll() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        List<Flight> actual = flightService.search(params);
        assertEquals(3, actual.size());
    }
}
