package my.flightdb.flightdbserver.controller;
import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Grouping;
import my.flightdb.flightdbserver.service.GroupingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class GroupingTest {
    @Autowired
    GroupingRestController controller;

    @Autowired
    GroupingService service;

    @Before
    public void loadGroupingTestDB() {
        Grouping grouping;

        grouping = Grouping.builder().fieldName("departure_airport").fieldValue("KORD").count(3).build();
        Grouping returnedGrouping = service.save(grouping);

        grouping = Grouping.builder().fieldName("departure_airport").fieldValue("KCMH").count(1).build();
        service.save(grouping);

        grouping = Grouping.builder().fieldName("arrival_airport").fieldValue("ABC0").count(3).build();
        service.save(grouping);

        grouping = Grouping.builder().fieldName("arrival_airport").fieldValue("APT").count(4).build();
        service.save(grouping);

        grouping = Grouping.builder().fieldName("tail_number").fieldValue("XYZ01").count(1).build();
        service.save(grouping);

        grouping = Grouping.builder().fieldName("tail_number").fieldValue("XYZ02").count(2).build();
        service.save(grouping);
    }

    @Test
    public void shouldReturnDepartureGroupings() {
        ResponseEntity<Collection<Grouping>> response = controller.departureAirports(PageRequest.of(0, 20));
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Collection<Grouping> groupings = response.getBody();
        assertEquals(2, groupings.size());
        Iterator<Grouping> it = groupings.iterator();
        Grouping grouping = it.next();
        assertEquals("departure_airport", grouping.fieldName);
        assertEquals("KORD", grouping.fieldValue);
        assertEquals(3, grouping.count);

        grouping = it.next();
        assertEquals("departure_airport", grouping.fieldName);
        assertEquals("KCMH", grouping.fieldValue);
        assertEquals(1, grouping.count);
    }

    @Test
    public void shouldReturnArrivalGroupings() {
        ResponseEntity<Collection<Grouping>> response = controller.arrivalAirports(PageRequest.of(0, 20));
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Collection<Grouping> groupings = response.getBody();
        assertEquals(2, groupings.size());
        Iterator<Grouping> it = groupings.iterator();
        Grouping grouping = it.next();
        assertEquals("arrival_airport", grouping.fieldName);
        assertEquals("ABC0", grouping.fieldValue);
        assertEquals(3, grouping.count);

        grouping = it.next();
        assertEquals("arrival_airport", grouping.fieldName);
        assertEquals("APT", grouping.fieldValue);
        assertEquals(4, grouping.count);
    }

    @Test
    public void shouldReturnTailNumberGroupings() {
        ResponseEntity<Collection<Grouping>> response = controller.tailNumbers(PageRequest.of(0, 20));
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Collection<Grouping> groupings = response.getBody();
        assertEquals(2, groupings.size());
        Iterator<Grouping> it = groupings.iterator();
        Grouping grouping = it.next();
        assertEquals("tail_number", grouping.fieldName);
        assertEquals("XYZ01", grouping.fieldValue);
        assertEquals(1, grouping.count);

        grouping = it.next();
        assertEquals("tail_number", grouping.fieldName);
        assertEquals("XYZ02", grouping.fieldValue);
        assertEquals(2, grouping.count);

    }
}
