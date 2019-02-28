package my.flightdb.flightdbserver.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Flight extends BaseEntity {

    private String tailNumber;
    private String aircraftType;
    private LocalDateTime startDateTime;

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}
