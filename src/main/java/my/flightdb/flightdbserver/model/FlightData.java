package my.flightdb.flightdbserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FlightData extends BaseEntity {
    double time;
    double altitude;
    @Column(name = "groundspeed")
    double groundSpeed;
    double latitude;
    double longitude;
    Long flightId;
}
