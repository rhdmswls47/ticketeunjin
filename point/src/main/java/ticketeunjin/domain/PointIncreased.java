package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointIncreased extends AbstractEvent {

    private Integer id;
    private Integer reserveId;
    private String userId;
    private Integer point;
    private Integer pointAmount;

    public PointIncreased(Point aggregate) {
        super(aggregate);
    }

    public PointIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
