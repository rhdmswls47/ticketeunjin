# 클라우드 네이티브 아키텍처 (IaaS) 
1. 클라우드 아키텍처 구성, MSA 아키텍처 구성도

# 클라우드 네이티브 모델링 (Biz.) 
1. 도메인분석 - 이벤트스토밍
- 고객이 예매하고 싶은 공연을 예매한다
-  예매하면 티켓 금액의 5퍼센트를 포인트로 적립한다
- 공연별 선착순 수량이 마감되면 해당 공연을 예매할 수 없다
- 고객은 본인의 예매 이력을 볼 수 있다
  
![image](https://github.com/user-attachments/assets/003a9fd8-2ef0-42da-b379-586eb356b7cc)


# 클라우드 네이티브 개발 MSA (Dev.) 
1. 분산트랜잭션 - Saga
2. 보상처리 - Compensation
3. 단일 진입점 - Gateway
4. 분산 데이터 프로젝선 - CQRS

# 클라우드 네이티브 운영 (PaaS) 
1. 클라우드 배포 - Container 운영
2. 컨테이너 자동확장 - HPA
3. 컨테이너로부터 환경분리 - ConfigMap/Secret
4. 클라우드스토리지 활용 - PVC
5. 셀프 힐링/무정지배포 - Liveness/Rediness Probe
6. 서비스 메쉬 응용 - Mesh
7. 통합 모니터링 - Loggregation/Monitoring

