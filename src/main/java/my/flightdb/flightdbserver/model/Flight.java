package my.flightdb.flightdbserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Flight extends BaseEntity {

    private String tailNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String aircraftType;
    private LocalDateTime startDateTime;
}
