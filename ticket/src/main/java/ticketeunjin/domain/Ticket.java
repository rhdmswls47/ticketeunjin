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
        //TicketDecreased ticketDecreased = new TicketDecreased(this);
        //ticketDecreased.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
       // TicketSoldout ticketSoldout = new TicketSoldout(this);
        //ticketSoldout.publishAfterCommit();
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

        repository().findById(ticketReserved.getShowId()).ifPresent(ticket->{

            ticket.setReserveId(ticketReserved.getId());
            ticket.setUserId(ticketReserved.getUserId());

            if(ticket.getStock() >= ticketReserved.getQty()){
                ticket.setStock(ticket.getStock() - ticketReserved.getQty());
                ticket.setStatus("Reservation Completed");
                repository().save(ticket);

                TicketDecreased ticketDecreased = new TicketDecreased(ticket);
                ticketDecreased.publishAfterCommit();
           
            }else{
                TicketSoldout ticketSoldout = new TicketSoldout(ticket);
                ticketSoldout.setReserveId(ticketReserved.getShowId());
                ticketSoldout.setStatus("Reservation Failed");
                ticketSoldout.publishAfterCommit();
            }
           
        });

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
