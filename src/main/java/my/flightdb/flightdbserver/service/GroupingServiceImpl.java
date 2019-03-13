package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.Grouping;
import my.flightdb.flightdbserver.repository.GroupingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupingServiceImpl implements GroupingService {

    private final GroupingRepository repository;

    public GroupingServiceImpl(GroupingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Grouping> findAll(Pageable pageable) {
        List<Grouping> groupings = new ArrayList<>();
        repository.findAll(pageable).forEach(groupings::add);
        return groupings;
    }

    @Override
    public Grouping save(Grouping object) {
        return repository.save(object);
    }
}
