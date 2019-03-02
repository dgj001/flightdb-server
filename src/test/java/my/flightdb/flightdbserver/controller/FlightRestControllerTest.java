package my.flightdb.flightdbserver.controller;

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

import static org.junit.Assert.assertEquals;

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
}
