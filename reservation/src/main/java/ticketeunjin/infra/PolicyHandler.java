package ticketeunjin.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ticketeunjin.config.kafka.KafkaProcessor;
import ticketeunjin.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ReservationRepository reservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TicketSoldout'"
    )
    public void wheneverTicketSoldout_UpdateStatus(
        @Payload TicketSoldout ticketSoldout
    ) {
        TicketSoldout event = ticketSoldout;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + ticketSoldout + "\n\n"
        );

        // Sample Logic //
        Reservation.updateStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
