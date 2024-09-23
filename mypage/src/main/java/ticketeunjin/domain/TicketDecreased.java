package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import ticketeunjin.infra.AbstractEvent;

@Data
public class TicketDecreased extends AbstractEvent {

    private Integer id;
    private Integer reserveId;
    private Integer showId;
    private String showName;
    private Integer stock;
    private String status;
    private Integer amount;
    private String userId;
}
