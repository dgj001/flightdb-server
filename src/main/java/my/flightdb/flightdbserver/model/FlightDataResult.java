package my.flightdb.flightdbserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FlightDataResult {
    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;
    private int numRecords;
    private List<FlightData> records = new ArrayList<>();
}
