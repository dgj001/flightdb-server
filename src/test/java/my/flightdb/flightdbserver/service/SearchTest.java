package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SearchTest {
    @Autowired
    FlightService flightService;

    final String F1_TAIL = "XYZ01";
    final String F1_TYPE = "Cessna 172";

    final String F2_TAIL = "XYZ02";
    final String F2_TYPE = "15 Number first";

    final String F3_TAIL = "XYZ03";
    final String F3_TYPE = ""; // empty

    @Before
    public void initializeTestRecords() {
        if (flightService.count() > 0) {
            return;
        }

        Flight flight;

        flight = new Flight();
        flight.setAircraftType(F1_TYPE);
        flight.setTailNumber(F1_TAIL);
        flightService.save(flight);

        flight = new Flight();
        flight.setAircraftType(F2_TYPE);
        flight.setTailNumber(F2_TAIL);
        flightService.save(flight);

        flight = new Flight();
        flight.setAircraftType(F3_TYPE);
        flight.setTailNumber(F3_TAIL);
        flightService.save(flight);
    }

    @Test
    public void searchWithoutParamsShouldGetAll() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        List<Flight> actual = flightService.search(FlightPredicate.build(params));
        assertEquals(3, actual.size());
    }

    @Test
    public void searchWithOneTypeShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("aircraftType", "Cessna 172");
        List<Flight> actual = flightService.search(FlightPredicate.build(params));
        assertEquals(1, actual.size());
        assertEquals(F1_TAIL, actual.get(0).getTailNumber());
    }

    @Test
    public void searchWithMultipleValuesShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("aircraftType", "Cessna 172");
        params.add("aircraftType", "");
        List<Flight> actual = flightService.search(FlightPredicate.build(params));
        assertEquals(2, actual.size());
        List<String> tailNos = new ArrayList<>();
        actual.forEach((item) -> tailNos.add(item.getTailNumber()));
        assertTrue(tailNos.contains(F1_TAIL));
        assertTrue(tailNos.contains(F3_TAIL));
    }

    @Test
    public void searchWithComplexConditionShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("aircraftType", F1_TYPE);
        params.add("aircraftType", F2_TYPE);
        params.add("tailNumber", F2_TAIL);
        List<Flight> actual = flightService.search(FlightPredicate.build(params));
        assertEquals(1, actual.size());
        assertEquals(F2_TAIL, actual.get(0).getTailNumber());
    }

    @Test
    public void searchWithNoResultShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


        params.add("aircraftType", F1_TAIL);
        Predicate predicate = FlightPredicate.build(params);
        List<Flight> actual = flightService.search(predicate);
        assertEquals(0, actual.size());

        params.clear();
        params.add("aircraftType", F1_TYPE);
        params.add("tailNumber", F2_TAIL);
        actual = flightService.search(predicate);
        assertEquals(0, actual.size());
    }
}
