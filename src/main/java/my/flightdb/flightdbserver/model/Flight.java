package my.flightdb.flightdbserver.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight extends BaseEntity {

    private String tailNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String aircraftType;
    private LocalDateTime startDateTime;
}
