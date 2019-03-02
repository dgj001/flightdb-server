package my.flightdb.flightdbserver.controller;

import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightRestControllerTest {

    @Autowired
    FlightRestController controller;

    @Test
    public void shouldReturnSearchResult() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        ResponseEntity<SearchResult> response = controller.search(params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SearchResult result = response.getBody();
        assertEquals(3L, (long) result.getCount());
        assertEquals(3L, result.getFlights().size());
    }

    @Test
    public void shouldReturnEmptyResponse() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("aircraftType", "non-existent type");
        ResponseEntity<SearchResult> response = controller.search(params);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void aircraftTypesShouldWork() {
        ResponseEntity<Collection<String>> response = controller.aircraftTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Collection<String> types = response.getBody();
        assertEquals(3, types.size());
        assertTrue(types.contains(TestDB.TYPE1));
        assertTrue(types.contains(TestDB.TYPE2));
        assertTrue(types.contains(TestDB.TYPE3));
    }

    @Test
    public void tailNumbersShouldWork() {
        ResponseEntity<Collection<String>> response = controller.tailNumbers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Collection<String> tailNos = response.getBody();
        assertEquals(3, tailNos.size());
        assertTrue(tailNos.contains(TestDB.TAIL1));
        assertTrue(tailNos.contains(TestDB.TAIL2));
        assertTrue(tailNos.contains(TestDB.TAIL3));
    }
}
