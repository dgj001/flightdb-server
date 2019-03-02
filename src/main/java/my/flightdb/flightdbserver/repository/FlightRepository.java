package my.flightdb.flightdbserver.repository;

import my.flightdb.flightdbserver.model.Flight;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<Flight, Long>, QuerydslPredicateExecutor<Flight> {
}
