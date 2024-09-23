package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import ticketeunjin.infra.AbstractEvent;

@Data
public class TicketReserved extends AbstractEvent {

    private Integer id;
    private Integer userid;
    private Integer showId;
    private String showName;
    private Integer qty;
    private Integer amount;
    private String status;
}
