package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import ticketeunjin.infra.AbstractEvent;

@Data
public class PointIncreased extends AbstractEvent {

    private Integer id;
    private Integer point;
    private Integer reserveId;
}
