package ticketeunjin.domain;

import java.util.*;
import lombok.*;
import ticketeunjin.domain.*;
import ticketeunjin.infra.AbstractEvent;

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
    private Integer userId;
}
