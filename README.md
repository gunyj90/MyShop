# Homework 프로젝트 설명

상품주문 프로그램을 제공합니다.

상품 리스트를 참고하여 상품번호와 수량을 입력하고 주문서를 작성합니다.

주문서를 토대로 주문과 결제가 이루어 집니다.

여러 상품을 한번에 주문할 수 있으며 주문 후, 주문금액, 배송비, 총 지불금액을 확인할 수 있습니다.

# 실행방법

1. 프로젝트 설치 경로로 이동한다.

2. ./gradlew bootJar 명령어를 실행하여 jar파일을 생성한다.

3. ./gradlew bootRun 명령어로 서버를 기동한다.

서버기동 후 H2콘솔 및 Swagger접속이 가능합니다.
* [H2콘솔로 접속하기](http://localhost:8080/h2-console)

# 프로젝트 설계

### ER 다이어그램

<img width="480" alt="layer" src="https://github.com/user-attachments/assets/a5520593-51db-4ee6-8247-ee09b4ecafc1">

* ORDERS: 주문 테이블, 
* ORDER_ITEM: 주문상품 테이블, 
* ITEM: 상품 테이블


### 클래스 다이어그램

<img width="880" alt="layer" src="https://github.com/user-attachments/assets/1b45434c-525c-47d5-802e-4ca742c1ed0a">

* 위 표를 통해 클래스의 의존관계와 상속, 구현에 대한 정보를 알 수 있습니다.

### 패키지 다이어그램

<img width="480" alt="layer" src="https://github.com/user-attachments/assets/f81cab56-b7a9-48d5-872c-6c6da523ee3c">

* 도메인은 POJO로 구성되어 있고 어떤 layer도 참조하지 않습니다.
구체적 기술이 포함된 infrastructure는 어떤 layer로부터 참조되지 않습니다.
이는 도메인으로 부터 비즈니스 도메인을 이해하고 기술적인 부분을 분리하여 변경에 유용하게 설계하기 위함입니다.

### 문제인식 및 해결

**[한 유저가 같은 광고를 여러번 참여할 수 있다]** : 이는 요구사항에 없는 내용으로 충분히 가능한 케이스로 고려되어졌습니다. 
광고entity와 1:n관계를 형성하고 유저와 광고id를 키값으로 생성후 2번째 참여부터 update되는 방식으로 구현하였습니다.


**[광고에 제약조건이 존재한다]** : 현재 3가지의 제약조건이 존재하고 추가될 가능성을 고려하여 constraint_info라는 테이블을 생성하였습니다. 
이후 광고생성시 요청받은 제약조건코드(c1,c2,c3)을 가지고 광고당 제약조건 데이터를 생성하였습니다.
광고조회 및 광고참여 시에 동적으로 제약조건을 체크하여 유효한 광고를 반환합니다. 제약조건이 설정되지 않은 광고는 
기본적인 내용인 참여가능횟수 및 기간만 체크하도록 합니다.


**[하나의 광고를 여러 유저가 참여할 수 있다]** : 유저가 광고에 참여하면 광고의 참여가능횟수를 1씩 줄입니다. 
동시성 문제를 고려하여 갱신시에 참여가능횟수가 0보다 큰 조건을 추가했습니다. 
