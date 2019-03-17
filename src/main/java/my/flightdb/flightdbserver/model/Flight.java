package my.flightdb.flightdbserver.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Flight extends BaseEntity {

    private String tailNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String aircraftType;
    private LocalDateTime startDateTime;

    @JsonInclude()
    @Transient
    private List<FlightData> records = new ArrayList<>();
}
