package ticketeunjin.domain;

import ticketeunjin.domain.TicketReserved;
import ticketeunjin.ReservationApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;


@Entity
@Table(name="Reservation_table")
@Data

//<<< DDD / Aggregate Root
public class Reservation  {


    
    @Id
    
    
    
    
    
    private Integer id;
    
    
    
    
    private Integer userId;
    
    
    
    
    private Integer showId;
    
    
    
    
    private String showName;
    
    
    
    
    private Integer qty;
    
    
    
    
    private Integer amount;
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        TicketReserved ticketReserved = new TicketReserved(this);
        ticketReserved.publishAfterCommit();

    
    }

    public static ReservationRepository repository(){
        ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(ReservationRepository.class);
        return reservationRepository;
    }



    public void ticketReserve(){
        //implement business logic here:
        
        TicketReserved ticketReserved = new TicketReserved(this);
        ticketReserved.publishAfterCommit();
        
        /*
        ticketeunjin.external.ReservationQuery reservationQuery = new ticketeunjin.external.ReservationQuery();
        ReservationApplication.applicationContext
            .getBean(ticketeunjin.external.Service.class)
            .( reservationQuery);
        */
    }

//<<< Clean Arch / Port Method
    public static void updateStatus(TicketSoldout ticketSoldout){
        
        //implement business logic here:

        repository().findById(ticketSoldout.getReserveId()).ifPresent(reservation->{
            reservation.setStatus("Reservation Failed");
            repository().save(reservation);
        });

        
    }
//>>> Clean Arch / Port Method


}
//>>> DDD / Aggregate Root
