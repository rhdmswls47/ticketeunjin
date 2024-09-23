package ticketeunjin.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ticketeunjin.config.kafka.KafkaProcessor;
import ticketeunjin.domain.*;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketReserved_then_CREATE_1(
        @Payload TicketReserved ticketReserved
    ) {
        try {
            if (!ticketReserved.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(ticketReserved.getUserid());
            mypage.setShowName(ticketReserved.getShowName());
            mypage.setQty(ticketReserved.getQty());
            mypage.setAmount(ticketReserved.getAmount());
            mypage.setReserveId(ticketReserved.getId());
            mypage.setReserveStatus("Reservation Requested");
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketSoldout_then_UPDATE_1(
        @Payload TicketSoldout ticketSoldout
    ) {
        try {
            if (!ticketSoldout.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByReserveId(
                ticketSoldout.getReserveId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setReserveStatus("Reservation Failed");
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketDecreased_then_UPDATE_2(
        @Payload TicketDecreased ticketDecreased
    ) {
        try {
            if (!ticketDecreased.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByReserveId(
                ticketDecreased.getReserveId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setReserveStatus("Reservation Completed");
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointIncreased_then_UPDATE_3(
        @Payload PointIncreased pointIncreased
    ) {
        try {
            if (!pointIncreased.validate()) return;
            // view 객체 조회

            List<Mypage> mypageList = mypageRepository.findByReserveId(
                pointIncreased.getReserveId()
            );
            for (Mypage mypage : mypageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                mypage.setPoint(pointIncreased.getPoint());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
