package my.flightdb.flightdbserver.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightData extends BaseEntity {
    Long flightId;
    double time;
    double altitude;
    @Column(name = "groundspeed")
    double groundSpeed;
    double latitude;
    double longitude;
    int phase;
    double verticalspeed;
    double lonAccel;
    double heading;
    double tas;
    double roll;
    double fuelQty_1;
    double fuelQty_2;
    double fuelQty_3;
    double fuelQty_4;
    double latAccel;
    double course;
    double pitch;
}
