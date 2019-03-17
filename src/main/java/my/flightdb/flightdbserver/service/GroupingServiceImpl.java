package my.flightdb.flightdbserver.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
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
    public List<Grouping> findAll(String fieldName, Pageable pageable) {
        List<Grouping> groupings = new ArrayList<>();
        Predicate predicate = getPredicate(fieldName);
        repository.findAll(predicate, pageable).forEach(groupings::add);
        return groupings;
    }

    private Predicate getPredicate(String fieldNameValue) {
        PathBuilder<Grouping> entityPath = new PathBuilder<>(Grouping.class, "grouping");
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(entityPath.getString("fieldName").equalsIgnoreCase(fieldNameValue));
        return builder;
    }

    @Override
    public Grouping save(Grouping object) {
        return repository.save(object);
    }
}
