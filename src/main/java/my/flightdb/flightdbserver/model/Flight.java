package my.flightdb.flightdbserver.model;

import javax.persistence.Entity;

@Entity
public class Flight extends BaseEntity {

    private String tailNumber;

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }
}
