package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "Mypage_table")
@Data
public class Mypage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String userId;
    private Integer reserveId;
    private String showName;
    private Integer qty;
    private Integer amount;
    private String reserveStatus;
    private Integer point;
}
