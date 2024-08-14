# MSA-Project

### MSA를 활용한 간단한 제품 주문 프로그램
  
<br />

  
# ⚙️ 기술 스택

### Back-End

<div>
  <img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>
  <img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/>
  <img alt="Gradle" src ="https://img.shields.io/badge/Gradle-02303A.svg?&style=for-the-badge&logo=Gradle&logoColor=white"/>
  <img alt="Spring Security" src ="https://img.shields.io/badge/Spring Security-6DB33F.svg?&style=for-the-badge&logo=Spring Security&logoColor=white"/>
  <img alt="Eureka" src ="https://img.shields.io/badge/Eureka-6DB33F.svg?&style=for-the-badge&logo=Eureka&logoColor=white"/>
  
</div>
<div>
  <img alt="MySQL" src ="https://img.shields.io/badge/MySQL-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=white"/>
  <img alt="Eureka" src ="https://img.shields.io/badge/Redis-FF4438.svg?&style=for-the-badge&logo=Redis&logoColor=white"/>
  <img alt="Hibernate" src ="https://img.shields.io/badge/Hibernate-59666C.svg?&style=for-the-badge&logo=Hibernate&logoColor=white"/>
</div>


<br />

## ☁️ 프로젝트 요구 사항

### 1️⃣ 패키지명 규칙과 포트 규칙 준수
#### &nbsp;&nbsp; 1. 패키지명은 com.sparta.msa_exam 으로 설정하고 유레카 서버는 19090 포트로 실행되도록 설정해주세요. 
#### &nbsp;&nbsp; 2. 게이트웨이 서비스는 com.sparta.msa_exam.gateway 패키지로 추가하고 19091 포트로 실행되도록 설정해주세요.
#### &nbsp;&nbsp; 3. 상품 서비스를 com.sparta.msa_exam.product 패키지로 추가하고 19093 포트로 실행되도록 설정해주세요.
#### &nbsp;&nbsp; 4. 주문 서비스를 com.sparta.msa_exam.order 패키지로 추가하고 19092 포트로 실행하도록 설정해주세요.
#### &nbsp;&nbsp; 5. 인증 서비스를 com.sparta.msa_exam.auth 패키지로 추가하고 19095 포트로 실행하도록 설정해주세요.

<br />

### 2️⃣ 필수 기능
- [ ]  **기본 API 구성하기**
    - API 목록
        1. `POST /products`  상품 추가 API 
        **상품 Entity**
            
            
            | Key | Value |
            | --- | --- |
            | product_id | Long (Primary, Auto Increment) |
            | name | String |
            | supply_price | Integer |
            
            **상품 추가시 Request/Response 객체 구성은 자유입니다.**
            
        2. `GET /products` 상품 목록 조회 API
            
            **응답 형태: List<응답 객체>**
            
            **응답 객체**
            
            | Key | Value |
            | --- | --- |
            | product_id | Long |
            | name | String |
            | supply_price | Integer |
            
            **상품 목록 조회시 Request 객체 구성은 자유입니다.**
            
        3. `POST /order` 주문 추가 API
            
            **주문 Entity**
            
            | Key | Value |
            | --- | --- |
            | order_id | Long (Primary, Auto Increment) |
            | name | String |
            | product_ids |List<주문 매핑 상품 Entity>|
            
            **Request/Response 객체 구성은 자유입니다.**
            
            **주문 매핑 상품 Entity**
            
            | Key | Value |
            | --- | --- |
            | id | Long (Primary, Auto Increment) |
            | order |주문 Entity|
            | product_id | Long |
        4. `PUT /order/{orderId}`  주문에 상품을 추가하는 API
            
            **요청 Body**
            
            | Key | Value |
            | --- | --- |
            | product_id | Long |
            
            **Request/Response 객체 구성은 자유입니다.**
            
        5. `GET /order/{orderId}`  주문 단건 조회 API
            
            **응답 객체**
            
            | Key | Value |
            | --- | --- |
            | order_id | Long |
            | product_ids | List<Long> |
        6. `GET /auth/signIn?user_id={string}`  로그인 API 
            
            DB 연결이 되지 않아도 됩니다. MSA 강의 문서를 참고해서 Gateway 서비스의 Filter 만 통과할 수 있도록 구성해주세요.

<br />

### 3️⃣ 상품 서비스는 라운드로빈 형식으로 로드밸런싱 구성하기
#### &nbsp;&nbsp; 1. IntelliJ Configuration 을 이용하여 상품 서비스를 `19094` 포트로 하나 더 실행 해보세요.
#### &nbsp;&nbsp; 2. 라운드로빈 형식으로 로드밸런싱을 구현해서 상품 서비스가 호출될 때마다 두 서비스를 반복하며 호출되게 구성해 보세요.
#### &nbsp;&nbsp; 3. 상품 목록을 조회할 때마다 API 의 응답 헤더의 `Server-Port` 값이 `19093` , `19094` 로 변경되어야 합니다.

<br />

### 4️⃣ 주문에 상품을 추가하는 API 만들 때 아래와 같이 구성해보세요.
#### &nbsp;&nbsp; 1. `FeignClient`를 이용해서 주문 서비스에 상품 서비스 클라이언트 연결
#### &nbsp;&nbsp; 2. 상품 목록 조회 API를 호출해서 파라미터로 받은 `product_id` 가 상품 목록에 존재하는지 검증
#### &nbsp;&nbsp; 3. 존재할경우 주문에 상품을 추가하고, 존재하지 않는다면 주문에 저장하지 않음.

<br />

### 5️⃣ 분산추적 구현해보기
#### &nbsp;&nbsp; 1. 주문 서비스 와 상품 서비스 에 Zipkin 을 연동하고, 호출시 Zipkin 대시보드에 Duration 이 측정 되는지 확인해보세요.

<br />

### 6️⃣ 캐싱 기능 구현하기
#### &nbsp;&nbsp; 1. 주문 조회 API 의 결과를 캐싱 처리하여 60초 동안은 DB 에서 불러오는 데이터가 아닌 메모리에 캐싱된 데이터가 보여지도록 설정해주세요.

<br />

### 7️⃣ 외부 요청 보호
#### &nbsp;&nbsp; 1. Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 상품 서비스, 주문 서비스를 호출할 때 401 HTTP Status Code를 응답하도록 설정해주세요.

<br />


