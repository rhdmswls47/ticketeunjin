# 클라우드 네이티브 아키텍처 (IaaS) 
### 1. 클라우드 아키텍처 구성, MSA 아키텍처 구성도
![image](https://github.com/user-attachments/assets/4c8a2a09-83a9-4311-942b-8908ad42a613)
# 클라우드 네이티브 모델링 (Biz.) 
### 1. 도메인분석 - 이벤트스토밍
- 고객이 예매하고 싶은 공연을 예매한다
-  예매하면 티켓 금액의 5퍼센트를 포인트로 적립한다
- 공연별 선착순 수량이 마감되면 해당 공연을 예매할 수 없다
- 고객은 본인의 예매 이력을 볼 수 있다
  
![image](https://github.com/user-attachments/assets/003a9fd8-2ef0-42da-b379-586eb356b7cc)


# 클라우드 네이티브 개발 MSA (Dev.) 
### 1. 분산트랜잭션 - Saga  
ticket, point에 사전 데이터 입력  

    http POST localhost:8083/tickets id=1 showId=1 showName="지킬앤하이드" stock=10 amount=150000  
    http POST localhost:8083/tickets id=2 showId=2 showName="하데스타운" stock=20 amount=160000  
    http POST localhost:8083/tickets id=3 showId=3 showName="어쩌면해피엔딩" stock=10 amount=80000  
  
    http POST localhost:8085/points id=1 point=0  
    http POST localhost:8085/points id=2 point=0  

예약 시도
![image](https://github.com/user-attachments/assets/4d6b502b-ef11-45bb-b220-2c8d3047b0c7)  
예약 결과
![image](https://github.com/user-attachments/assets/19eec17a-a0a8-4d9e-8686-c3b411bdf796)

### 2. 보상처리 - Compensation
구매할 수 있는 수를 초과하여 구매하기 시도
![image](https://github.com/user-attachments/assets/b43f9235-9852-4d9c-b4d9-e3379c75a83b)

시도결과 soldout처리됨
![image](https://github.com/user-attachments/assets/58e6a952-56e4-4393-9a5d-743e18e0e14a)

### 3. 단일 진입점 - Gateway  
게이트웨이 서비스 기동하여 8088로 접속  
![image](https://github.com/user-attachments/assets/c0f86ad6-3f84-475a-bd35-4397a34a256c)
![image](https://github.com/user-attachments/assets/ec279c4d-0d25-4478-994b-43897f662ce7)
### 4. 분산 데이터 프로젝션 - CQRS
데이터모델인 MyPage는 처음 주문 발생시 CREATE되며, 3가지 상황에 업데이트가 발생한다  

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketReserved_then_CREATE_1(
        @Payload TicketReserved ticketReserved
    ) {
        try {
            if (!ticketReserved.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(ticketReserved.getUserId());
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

mypages 조회  
![image](https://github.com/user-attachments/assets/a4f86127-57a1-4204-9505-817deecbb505)
![image](https://github.com/user-attachments/assets/a47ecdac-2d3e-4fc6-9331-fa80c5d5f760)

# 클라우드 네이티브 운영 (PaaS) 
### 1. 클라우드 배포 - Container 운영
Azure DevOps를 사용하여 reservation CI/CD 진행  

Pipeline CI  
![image](https://github.com/user-attachments/assets/a8ef4254-a96e-4c90-ad18-55424749ab36)
![image](https://github.com/user-attachments/assets/c0e9ee08-3d70-4af2-afea-4a44cc79a472)

Pipeline CD
![image](https://github.com/user-attachments/assets/eb700996-0b0f-4cd0-a5ff-adfb9b0857e4)
![image](https://github.com/user-attachments/assets/101cc0bf-9681-41ae-9353-7727c22a5dca)


### 2. 컨테이너 자동확장 - HPA
Auto Scale out 설정

    kubectl scale deploy reservation --replicas=3
    kubectl autoscale deployment reservation --cpu-percent=50 --min=1 --max=3

![image](https://github.com/user-attachments/assets/14d10e9d-bcde-464c-ab69-23c0af39245f)

Auto Scale out 확인  

    siege -c20 -t40S -v http://reservation:8080/reservations

![image](https://github.com/user-attachments/assets/6739a5d5-f6fd-4c08-a22e-3945ff2dfb62)


### 3. 컨테이너로부터 환경분리 - ConfigMap/Secret
사용할 수 있는 config를 ConfigMap에 저장하여 사용  
![image](https://github.com/user-attachments/assets/6a62448a-5a78-4579-807d-27d5de8b47b1)


### 4. 클라우드스토리지 활용 - PVC
yaml로 pvc 생성
![image](https://github.com/user-attachments/assets/4ef2ab1b-45bb-4194-b14e-a9afd8e52b22)
![image](https://github.com/user-attachments/assets/264a6da1-d33b-470c-9990-1fa4e6b01e55)

NFS볼륨을 가지는 예약서비스 배포
![image](https://github.com/user-attachments/assets/4534a867-5f99-4085-876e-f53fa862cd69)

파일시스템 마운트 확인, 다른pod에서도 확인
![image](https://github.com/user-attachments/assets/9a913de2-009f-417b-90d5-2f2a9e18867c)


### 5. 셀프 힐링/무정지배포 - Liveness/Rediness Probe

readinessProbe설정, siege 실행 중 버전 바꾸어 재배포  
![image](https://github.com/user-attachments/assets/b6eeeeba-9d63-4892-aa71-d03750fe9bb3)
![image](https://github.com/user-attachments/assets/6ce2286d-61c1-426e-90fc-58df0ed5d0ea)


### 6. 서비스 메쉬 응용 - Mesh
istio 설정 후 모든 pod 2/2 확인  
![image](https://github.com/user-attachments/assets/6db7ab5c-b46e-4c4d-b878-a5dad8cd12d7)
![image](https://github.com/user-attachments/assets/d84bd245-46a8-4d0c-9af6-8505fb9a3d72)


### 7. 통합 모니터링 - Loggregation/Monitoring
Prometheus 사용, siege로 요청 보내고 모니터링
![image](https://github.com/user-attachments/assets/fbd7f57f-b700-4ede-8c86-d92f0f714d5e)
![image](https://github.com/user-attachments/assets/073456a0-4099-4737-b8f2-f32ceb424f5d)
