package ticketeunjin.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import ticketeunjin.TicketApplication;
import ticketeunjin.domain.TicketDecreased;
import ticketeunjin.domain.TicketSoldout;

@Entity
@Table(name = "Ticket_table")
@Data
//<<< DDD / Aggregate Root
public class Ticket {

    @Id
    private Integer id;

    private Integer reserveId;

    private Integer showId;

    private String showName;

    private Integer stock;

    private String status;

    private Integer amount;

    private Integer userId;

    @PostPersist
    public void onPostPersist() {
        TicketDecreased ticketDecreased = new TicketDecreased(this);
        ticketDecreased.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        TicketSoldout ticketSoldout = new TicketSoldout(this);
        ticketSoldout.publishAfterCommit();
    }

    public static TicketRepository repository() {
        TicketRepository ticketRepository = TicketApplication.applicationContext.getBean(
            TicketRepository.class
        );
        return ticketRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseTicket(TicketReserved ticketReserved) {
        //implement business logic here:

        /** Example 1:  new item 
        Ticket ticket = new Ticket();
        repository().save(ticket);

        TicketDecreased ticketDecreased = new TicketDecreased(ticket);
        ticketDecreased.publishAfterCommit();
        TicketSoldout ticketSoldout = new TicketSoldout(ticket);
        ticketSoldout.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(ticketReserved.get???()).ifPresent(ticket->{
            
            ticket // do something
            repository().save(ticket);

            TicketDecreased ticketDecreased = new TicketDecreased(ticket);
            ticketDecreased.publishAfterCommit();
            TicketSoldout ticketSoldout = new TicketSoldout(ticket);
            ticketSoldout.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
