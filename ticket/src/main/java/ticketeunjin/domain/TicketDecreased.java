package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TicketDecreased extends AbstractEvent {

    private Integer id;
    private Integer reserveId;
    private Integer showId;
    private String showName;
    private Integer stock;
    private String status;
    private Integer amount;

    public TicketDecreased(Ticket aggregate) {
        super(aggregate);
    }

    public TicketDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
