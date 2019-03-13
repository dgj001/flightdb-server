package my.flightdb.flightdbserver.model;

import lombok.*;

import javax.persistence.Entity;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grouping extends BaseEntity {
    public String fieldName;
    public String fieldValue;
    public int count;
}
