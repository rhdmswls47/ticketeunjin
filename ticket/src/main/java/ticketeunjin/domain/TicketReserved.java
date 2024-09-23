package ticketeunjin.domain;

import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

@Data
@ToString
public class TicketReserved extends AbstractEvent {

    private Integer id;
    private Integer userId;
    private Integer showId;
    private String showName;
    private Integer qty;
    private Integer amount;
    private String status;
}
