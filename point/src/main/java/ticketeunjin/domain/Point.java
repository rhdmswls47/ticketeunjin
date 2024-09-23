package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import ticketeunjin.PointApplication;
import ticketeunjin.domain.PointIncreased;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    private Integer id;

    private Integer point;

    private Integer reserveId;

    @PostPersist
    public void onPostPersist() {
        PointIncreased pointIncreased = new PointIncreased(this);
        pointIncreased.publishAfterCommit();
    }

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public static void increasePoint(TicketDecreased ticketDecreased) {
        //implement business logic here:

        repository().findById(ticketDecreased.getUserId()).ifPresent(point->{
            point.setPoint(point.getPoint() + (int)(ticketDecreased.getAmount()*0.05));
            repository().save(point);

            PointIncreased pointIncreased = new PointIncreased(point);
            pointIncreased.publishAfterCommit();
        });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
