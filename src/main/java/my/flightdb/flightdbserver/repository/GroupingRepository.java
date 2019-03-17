package my.flightdb.flightdbserver.repository;

import my.flightdb.flightdbserver.model.Grouping;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface GroupingRepository extends PagingAndSortingRepository<Grouping, Long>, QuerydslPredicateExecutor<Grouping> {
}
