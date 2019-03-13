package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Grouping;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupingService {
    List<Grouping> findAll(Pageable pageable);
    Grouping save(Grouping object);
}
