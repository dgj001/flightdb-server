package my.flightdb.flightdbserver.repository;

import my.flightdb.flightdbserver.model.Flight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlightRepository extends PagingAndSortingRepository<Flight, Long>, QuerydslPredicateExecutor<Flight> {
    @Query("select DISTINCT(f.aircraftType) FROM Flight f")
    List<String> findDistinctAircraftTypes();

    @Query("SELECT DISTINCT(f.tailNumber) FROM Flight f")
    List<String> findDistinctTailNumbers();

    @Query("SELECT DISTINCT(f.departureAirport) FROM Flight f")
    List<String> findDistinctDepartureAirports();

    @Query("SELECT DISTINCT(f.arrivalAirport) FROM Flight f")
    List<String> findDistinctArrivalAirports();
}
