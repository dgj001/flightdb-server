package my.flightdb.flightdbserver.controller;

import my.flightdb.flightdbserver.TestDB;
import my.flightdb.flightdbserver.model.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaginationTest {

    @Autowired
    FlightRestController controller;

    @Autowired
    TestDB testDB;

    @Test
    public void shouldReturnPaginatedResults() {
        testDB.createPaginationDB();

        int page = 0;
        int size = 3;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));

        ResponseEntity<SearchResult> response = controller.search(params, PageRequest.of(page, size));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SearchResult result = response.getBody();
        assertEquals(20, (long) result.getCount());
        assertEquals(size, result.getFlights().size());
        assertEquals("tail0", result.getFlights().get(0).getTailNumber());
        assertEquals("tail1", result.getFlights().get(1).getTailNumber());
        assertEquals("tail2", result.getFlights().get(2).getTailNumber());

        page = 6;
        size = 3;

        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));

        response = controller.search(params, PageRequest.of(page, size));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        result = response.getBody();
        assertEquals(20, (long) result.getCount());
        assertEquals(2, result.getFlights().size());
        assertEquals("tail18", result.getFlights().get(0).getTailNumber());
        assertEquals("tail19", result.getFlights().get(1).getTailNumber());
    }
}
