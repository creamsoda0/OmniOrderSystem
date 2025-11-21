🛍️ OmniOrderSystem: 실시간 주문 처리 시스템
현재 진행중인 프로젝트(미완성)입니다.

<details>
<summary><strong>2025.11.21 + 업데이트 사항</strong></summary>

* DB는 PostgreSQL을 사용
* 현재 모든 시스템이 Docker 이미지로 구성
* Kubernetes를 통해 2중화 완료
* 파이프라인 구성을 위해 Skaffold 도입 시도 중
* 로컬 환경과 Kubernetes 환경의 HTTP 설정 통일을 위해
  Ingress 도입 시도 중

</details>

<details>
<summary><strong>2025.11.21 업데이트 사항</strong></summary>

* 각 서비스별 Dockerfile 생성
* Docker Hub에 이미지 push
* Kubernetes 환경 파일 생성 후 최상단 디렉터리 `k8s`에 정리
* Kubernetes 서비스 배포 시도 중 DB 설정 에러 발생

    * (Oracle 설정과 PostgreSQL 설정 충돌)
* DB 충돌 문제는 브랜치를 나눠 해결 예정

</details>

<details>
<summary><strong>2025.11.20 업데이트 사항</strong></summary>

* 주문 DB Transaction 실패 시 재고 복구 API 구현
* 테스트 코드 작성 완료
* 한글 UTF-8 인코딩 문제 수정 진행 중

</details>

<details>
<summary><strong>2025.11.17 업데이트 사항</strong></summary>

* 주문 처리 로직 MVP 구축
* MSA + OpenFeign 구조 완성
* 테스트 DB 구축
* 주문 시 재고 차감 로직 구현
* 사용자 인터페이스 간단 구현
* Docker에 Redis 설치 완료

</details>



🧑‍💻 프로젝트 개요 (Project Overview)
본 프로젝트는 1인 개발로 구축된 마이크로서비스 아키텍처(MSA) 기반의 실시간 주문 처리 시스템입니다. 동시성 제어, 서비스 간 통신, 컨테이너 오케스트레이션(Kubernetes)을 중점적으로 다루며, 대규모 트래픽 환경에서도 안정적인 서비스 운영이 가능하도록 설계 및 구현되었습니다.
모든 서비스는 Docker 컨테이너화되어 운영됩니다.

💻 핵심 기술 스택 (Core Technology Stack)

구분	기술 요소	버전
Backend	Java
| 21 |
| Framework | Spring Boot | 3.1.x |
| MSA | Spring Cloud (Eureka, OpenFeign) | 2022.0.x |
| Database | Oracle DB | 19c (또는 사용 버전) |
| Cache | Redis | 6.x |
| Container | Docker | Latest |
| Orchestration | Kubernetes | Latest |
| API | RESTful API | |



🏛️ 시스템 아키텍처 및 데이터 흐름
🖼️ 아키텍처 다이어그램
서비스 간의 역할 분담과 데이터 흐름을 보여주는 다이어그램입니다. (현재 구성을 나타냅니다. 프로젝트 진행에 따라 업데이트 예정입니다.)
![MSA 아키텍처 다이어그램](./20251117.png)

1. 서비스 역할 분담
   • eureka-server: 마이크로서비스들의 위치를 관리하고 서비스 등록 및 검색을 담당하는 Discovery Server입니다.
   • order-service: 주문 생성, 조회 등 핵심 비즈니스 로직을 담당하며, Redis 캐싱을 활용하여 성능을 개선합니다.
   • product-service: 상품 정보 관리 및 재고 차감 로직을 전담하며, Oracle DB에 접근하여 재고를 관리합니다.
2. 주요 데이터 흐름 (Order-to-Stock)
    1. Client → Order Service로 주문 요청 (REST API).
    2. Order Service는 Eureka Server를 통해 Product Service의 위치를 확인합니다.
    3. Order Service는 Feign Client를 사용하여 Product Service의 재고 차감 API를 호출합니다.
    4. 재고 차감 성공 응답을 받은 후, Order Service는 주문 정보를 Oracle DB에 최종 저장합니다.

⚙️ 실행 방법 (Getting Started)
1. 환경 요구사항
   • Java 21
   • Gradle 8.x
   • Docker & Minikube/Kubernetes (Deployment 시)
2. 설정 파일 수정
   각 서비스(order-service, product-service, omnieureka)의 application.yml 파일에서 데이터베이스 및 Eureka 관련 설정을 사용자 환경에 맞게 수정해야 합니다.
3. 애플리케이션 실행 순서
   각 서비스 프로젝트 폴더에서 Gradle을 통해 실행합니다.
    1. Eureka Server 실행 (./gradlew :omnieureka:bootRun)
    2. Order Service 실행 (./gradlew :order-service:bootRun)
    3. Product Service 실행 (./gradlew :product-service:bootRun)
