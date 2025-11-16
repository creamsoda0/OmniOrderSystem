# OmniOrderSystem

토이 프로젝트 : 실시간 주문처리 시스템입니다. (Restful API, Oracle DB, Redis, MSA, Docker, Kubernetes, CI/CD)



\# 🛍️ \[토이 프로젝트] 실시간 주문 처리 시스템 (Real-time Order Processing System)



| | |

| :---: | :---: |

| \[!\[Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/) | \[!\[Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.x-brightgreen.svg)](https://spring.io/projects/spring-boot) |

| \[!\[MSA](https://img.shields.io/badge/Architecture-MSA-orange.svg)]() | \[!\[Eureka](https://img.shields.io/badge/Discovery-Eureka-yellow.svg)]() |

| \[!\[Oracle](https://img.shields.io/badge/Database-Oracle%20DB-red.svg)]() | \[!\[Redis](https://img.shields.io/badge/Cache-Redis-darkred.svg)]() |

| \[!\[Docker](https://img.shields.io/badge/Container-Docker-informational.svg)](https://www.docker.com/) | \[!\[Kubernetes](https://img.shields.io/badge/Orchestration-Kubernetes-blue.svg)](https://kubernetes.io/) |



---



\## 🚀 프로젝트 개요 (Project Overview)



본 프로젝트는 \*\*마이크로서비스 아키텍처(MSA)\*\*를 기반으로 구축된 \*\*실시간 주문 처리 시스템\*\*입니다. 동시성 문제를 해결하고, 대규모 트래픽 환경에서도 안정적인 서비스 운영이 가능하도록 설계 및 구현되었습니다. 모든 서비스는 \*\*Docker\*\* 컨테이너화되어 \*\*Kubernetes\*\* 환경에서 관리됩니다.



\## 💻 핵심 기술 스택 (Core Technology Stack)



| 구분 | 기술 요소 | 버전 |

| :--- | :--- | :--- |

| \*\*Backend\*\* | \*\*Java\*\* | 21 |

| \*\*Framework\*\* | \*\*Spring Boot\*\* | 3.1.x |

| \*\*MSA\*\* | \*\*Spring Cloud\*\* | 2022.0.x (Eureka, OpenFeign) |

| \*\*Database\*\* | \*\*Oracle DB\*\* | 19c (또는 사용 버전) |

| \*\*Cache\*\* | \*\*Redis\*\* | 6.x |

| \*\*Infra/Deployment\*\* | \*\*Docker, Kubernetes\*\* | Latest |

| \*\*Automation\*\* | \*\*CI/CD 파이프라인\*\* | (Jenkins, GitHub Actions 등 명시 가능) |

| \*\*API Standard\*\* | \*\*RESTful API\*\* | |



\## 🏛️ 시스템 아키텍처 및 구성



서비스 간의 역할 분담과 데이터 흐름을 보여주는 아키텍처 다이어그램입니다. 



\### 1. 서비스 역할



\* \*\*`eureka-server`\*\*: 서비스 등록 및 검색을 담당하는 Discovery Server.

\* \*\*`order-service`\*\*: 주문 생성, 조회, 결제 처리 (Redis 캐싱 활용).

\* \*\*`product-service`\*\*: 상품 정보 관리, \*\*재고 차감 로직\*\* 처리 (Oracle DB 접근).



\### 2. 주요 데이터 흐름



1\.  \*\*Client\*\* → \*\*Order Service\*\*로 주문 요청 (REST API).

2\.  \*\*Order Service\*\*는 \*\*Eureka Server\*\*를 통해 \*\*Product Service\*\*의 위치를 파악.

3\.  \*\*Order Service\*\*는 \*\*Feign Client\*\*를 이용해 \*\*Product Service\*\*의 재고 차감 API를 호출.

4\.  재고 차감 성공 시, 주문 정보를 Oracle DB에 저장.



\## ✨ 주요 구현 내용 및 기술적 성과



\* \*\*실시간 동시성 문제 해결\*\*: 주문 트랜잭션 시 \*\*Redis\*\*를 활용한 락(Lock) 또는 \*\*Oracle\*\*의 동시성 제어 기능을 적용하여 대규모 트래픽 환경에서 발생하는 재고 불일치(Race Condition)를 최소화.

\* \*\*고가용성 확보 (High Availability)\*\*: 모든 서비스를 Dockerized하고 Kubernetes에 배포하여, 특정 서비스 인스턴스 장애 발생 시 자동 복구 및 수평 확장을 구현.

\* \*\*개발 생산성 증대\*\*: CI/CD 파이프라인을 구축하여 코드 병합(Merge)부터 컨테이너 이미지 생성 및 배포까지 자동화.



\## ⚙️ 실행 방법 (Getting Started)



\### 1. 환경 요구사항



\* Java 21

\* Gradle 8.x

\* Docker \& Minikube/Kubernetes (Deployment 시)



\### 2. 설정 파일 수정



각 서비스(`order-service`, `eureka-server`)의 `application.yml` 파일에서 데이터베이스 및 Eureka 관련 설정을 환경에 맞게 수정합니다.



\### 3. 애플리케이션 실행 순서



1\.  \*\*Eureka Server\*\* 실행 (`./gradlew :eureka-server:bootRun`)

2\.  \*\*Order Service\*\* 및 \*\*Product Service\*\* 실행 (`./gradlew :\[서비스명]:bootRun`)

