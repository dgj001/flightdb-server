package my.flightdb.flightdbserver.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import my.flightdb.flightdbserver.model.Flight;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class PredicateBuilder {

    public Predicate buildFlightPredicate(MultiValueMap<String, String> params) {
        BooleanBuilder searchBuilder = new BooleanBuilder();

        String[] fields = { "aircraftType", "tailNumber" };
        for (String fld : fields) {
            if (params.containsKey(fld)) {
                searchBuilder = searchBuilder.and(buildFlightOrPredicate(fld, params.get(fld)));
            }
        }
        return searchBuilder;
    }

    private Predicate buildFlightOrPredicate(String field, List<String> values) {
        PathBuilder<Flight> entityPath = new PathBuilder<>(Flight.class, "flight");
        BooleanBuilder builder = new BooleanBuilder();
        for (String val : values) {
            builder.or(entityPath.getString(field).equalsIgnoreCase(val));
        }
        return builder;
    }

}
