package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class TicketSoldout extends AbstractEvent {

    private Integer id;
    private Integer reserveId;
    private Integer showId;
    private String showName;
    private Integer stock;
    private String status;
    private Integer amount;
    private Integer userId;

    public TicketSoldout(Ticket aggregate) {
        super(aggregate);
    }

    public TicketSoldout() {
        super();
    }
}
//>>> DDD / Domain Event
