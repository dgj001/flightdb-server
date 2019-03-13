package my.flightdb.flightdbserver.service;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.Flight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class SearchTest {
    @Autowired
    FlightService flightService;

    @Autowired
    TestDB testDB;

    @Before
    public void createTestDB() {
        testDB.createSharedDB();
    }

    @Test
    public void searchWithoutParamsShouldGetAll() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        List<Flight> actual = flightService.search(FlightPredicate.build(params), PageRequest.of(0, 20));
        assertEquals(3, actual.size());
    }

    @Test
    public void searchWithOneTypeShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("aircraftType", TestDB.TYPE1);
        List<Flight> actual = flightService.search(FlightPredicate.build(params), PageRequest.of(0, 20));
        assertEquals(1, actual.size());
        assertEquals(TestDB.TAIL1, actual.get(0).getTailNumber());
    }

    @Test
    public void searchWithMultipleValuesShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("aircraftType", TestDB.TYPE1);
        params.add("aircraftType", "");
        List<Flight> actual = flightService.search(FlightPredicate.build(params), PageRequest.of(0, 20));
        assertEquals(2, actual.size());
        List<String> tailNos = new ArrayList<>();
        actual.forEach((item) -> tailNos.add(item.getTailNumber()));
        assertTrue(tailNos.contains(TestDB.TAIL1));
        assertTrue(tailNos.contains(TestDB.TAIL3));
    }

    @Test
    public void searchWithComplexConditionShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("aircraftType", TestDB.TYPE1);
        params.add("aircraftType", TestDB.TYPE2);
        params.add("tailNumber", TestDB.TAIL2);
        List<Flight> actual = flightService.search(FlightPredicate.build(params), PageRequest.of(0, 20));
        assertEquals(1, actual.size());
        assertEquals(TestDB.TAIL2, actual.get(0).getTailNumber());
    }

    @Test
    public void searchWithNoResultShouldWork() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("aircraftType", TestDB.TAIL1);
        Predicate predicate = FlightPredicate.build(params);
        List<Flight> actual = flightService.search(predicate, PageRequest.of(0, 20));
        assertEquals(0, actual.size());

        params.clear();
        params.add("aircraftType", TestDB.TYPE1);
        params.add("tailNumber", TestDB.TYPE2);
        actual = flightService.search(predicate, PageRequest.of(0, 20));
        assertEquals(0, actual.size());
    }
}
