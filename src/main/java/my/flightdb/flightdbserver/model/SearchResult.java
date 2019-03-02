package my.flightdb.flightdbserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResult {
    private Long count;
    private List<Flight> flights;
}
