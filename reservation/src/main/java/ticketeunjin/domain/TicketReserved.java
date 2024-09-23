package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TicketReserved extends AbstractEvent {

    private Integer id;
    private String userid;
    private Integer showId;
    private String showName;
    private Integer qty;
    private Integer amount;
    private String status;

    public TicketReserved(Reservation aggregate) {
        super(aggregate);
    }

    public TicketReserved() {
        super();
    }
}
//>>> DDD / Domain Event
