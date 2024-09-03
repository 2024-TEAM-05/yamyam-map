# 맛나지도 (yamyam-map)

## **Table of Contents**

- [개요](#개요)
- [테크스펙](#테크스펙)
- [Skills](#skills)
- [ERD](#erd)
- [API 명세서](#api-명세서)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)
- [Authors](#authors)

## 개요

“맛나지도(yamyam-map)”는 사용자가 주변 최고의 맛집을 찾을 수 있도록 돕는 것을 목표로 하는 위치 기반 맛집 추천 서비스입니다.  
[서울시 일반음식점 인허가 정보](https://data.seoul.go.kr/dataList/OA-16094/S/1/datasetView.do) 데이터를 활용하여, 지역 음식점 목록을 자동으로 업데이트하고
활용합니다.

## 테크스펙

<details>
	<summary> 리뷰 API </summary>

### **요약 (Summary)**

맛집에 대한 리뷰를 합니다.

### **목표 (Goals)**

1~5 점 사이의 점수와 리뷰를 달 수 있습니다.

### **목표가 아닌 것 (Non-Goals)**

사진을 올릴 수 있습니다.

### **계획 (Plan)**

```mermaid
graph TD
    A[리뷰 등록 요청] --> B[JWT 인증 확인]
    B -->|인증 실패| C[401 Unauthorized 반환]
    B -->|인증 성공| D[리뷰 생성, 새로운 평점 계산]
    D --> E[200 OK 반환]
```

### **마일스톤 (Milestones)**

> ~8월 28일 : 리뷰 엔티티 정의 <br>
~8월 29일: 기능 구현
>
</details>

<details>
	<summary> 시군구 목록 API </summary>

### **요약 (Summary)**

사용자는 도/광역시에 속한 시/군/구 목록을 조회할 수 있습니다.

### **목표 (Goals)**

- `도/광역시`에 속한 `시/군/구` 목록을 조회하는 API를 구현합니다.
- 효율적인 조회를 위해 시군구 데이터를 캐싱하여 성능을 최적화합니다.

### 요구사항 상세

- **설명**
    - 사용자는 도/광역시의 시/군/구 목록을 조회할 수 있습니다.

- **출력 데이터**
    - `도/광역시(province_name)`
    - `시/군/구 목록`
    - 각 시/군/구의 `위도(latitude)`, `경도(longitude)`

- **처리 과정**:
    1. 도/광역시로 그룹화한 시/군/구 목록을 조회합니다.
    2. 시/군/구 목록을 반환할 때, 시군구의 좌표 정보도 함께 포함됩니다.

- **예외 사항**
    - 시군구 데이터가 없을 시 빈 리스트를 반환합니다.

### **계획 (Plan)**

### Flowchart

```mermaid
flowchart TD
    A([사용자]) --> B{지역 목록 API 요청}
    B --> C[[지역 데이터 조회]]
    C --> D[시/도 기준 시/군/구 데이터 그룹화]
    D --> E[좌표 포함 시/군/구 목록 생성]
    E --> F[[그룹화된 시/군/구 목록 반환]]
```

```mermaid

erDiagram
Region {
	id BIGINT PK "AUTO_INCREMENT"
	province VARCHAR(50) "NOT NULL"
	city_district VARCHAR(50) "NOT NULL"
	location POINT "NOT NULL"
}
```

- DTO, 서비스, 레파지토리 구현
    - 도/광역시 및 시/군/구 데이터를 처리하고 검색하는 로직 작성

- 시군구 조회 컨트롤러 구현

- 테스트 코드 작성 및 기능 테스트
    - 시군구 조회 API
    - 통합 테스트

- 조회된 시군구 데이터를 캐싱하여 성능 최적화

### **이외 고려 사항들 (Other Considerations)**

- 캐시 만료: 시군구 데이터는 잘 변경되지 않으므로 캐시 만료 시간을 길게 설정?
- API 성능 테스트: 캐싱 전후의 성능 차이 테스트하고 최적화

### **마일스톤 (Milestones)**

- **8월 28일~8월 29일**: 엔티티 정의 및 시군구 조회 기능 설계
- **8월 30일**: 시군구 서비스 및 리포지토리 구현
- **8월 31일 ~ 9월 1일**: API 개발 및 시군구 관련 기능 구현
- **9월 2일**: 문서화 및 최종 점검
- **이후**: 캐싱 기능 구현 및 성능 최적화

</details>

<details>
		<summary> 맛집 목록(추천) API </summary>

### **요약 (Summary)**

사용자는 자신의 현재 위치 또는 선택한 특정 지역의 중심 좌표를 기준으로 일정 범위 내의 맛집 목록을 조회할 수 있습니다. 조회된 맛집 목록은 사용자의 요청에 따라 거리순 또는 평점순으로 정렬됩니다.

### **목표 (Goals)**

- `위도(Lat)`, `경도(Lon)`, `범위(Range)`를 기반으로 맛집 목록을 조회할 수 있습니다.
- 맛집 목록은 `거리순` 또는 `평점순`으로 정렬됩니다.
- 사용자는 "내 주변 보기" 또는 "특정 지역 보기" 기능을 사용하여 맛집 목록을 조회할 수 있습니다.

### **목표가 아닌 것 (Non-Goals)**

- 상세한 필터링 기능 (특정 음식 종류, 가격대 등)

### 요구사항 상세

- **설명**
    - 사용자는 현재 위치의 좌표를 기준으로 또는 선택한 특정 지역의 좌표를 기준으로 범위 내의 맛집 목록을 조회할 수 있습니다.
    - 맛집 목록은 사용자가 요청한 정렬 방식에 따라 거리순 또는 평점순으로 정렬합니다.

- **입력 데이터**
    - `위도(Lat)`
    - `경도(Lon)`
    - `범위(Range)` (단위: km)
    - `정렬방식(Sort)` - "거리순" 또는 "평점순"

- **출력 데이터**
    - `맛집 목록` - 이름, 종목, 위도/경도, 평점 등

- **처리 과정**
    1. 파라미터 위도(Lat), 경도(Lon), 범위(Range)를 기반으로 맛집 목록을 필터링
    2. 필터링된 맛집 목록을 사용자가 요청한 정렬 방식(거리순 또는 평점순)에 따라 정렬
    3. 정렬된 맛집 목록을 사용자에게 반환

- **예외 사항**
    - 위도/경도 또는 범위 값이 유효하지 않은 경우, 오류 메시지와 함께 조회 실패 응답을 반환
    - 정렬 방식이 "거리순" 또는 "평점순" 이외일 경우, 디폴트를 거리순으로 처리

### **계획 (Plan)**

### Flowchart

```mermaid
flowchart TD
    A[유저] -->|위도, 경도, 범위, 페이징, 정렬 요청| B{Validation 확인}
    B --> |위도, 경도, 범위 데이터 없음| K[400 해당 값은 필수 값이라는 에러 반환]
    B --> |Validation 통과| C{필터링}
    
    C -->|데이터 O| D{정렬 방식 확인}
    C -->|데이터 X| E[빈 리스트 반환]

    D -->|거리순| F[거리순 정렬]
    D -->|평점순| G[평점순 정렬]
    D -->|그 외| H[400 해당 정렬방식 제공하지 않는다는 에러 반환]

    F --> I[정렬된 맛집 목록 반환]
    G --> I
```

- DTO, 서비스, 레파지토리 구현
    - 위도/경도 및 범위를 사용한 맛집 필터링 로직 작성
    - 거리 계산 및 거리순, 평점순 정렬 로직 구현 (Hibernate Spatial 함수를 이용)

- 맛집 조회 컨트롤러 구현

- 테스트 코드 작성 및 기능 테스트
    - 거리 계산, 정렬 로직, 맛집 목록 API 유닛 테스트
    - 통합 테스트

### **이외 고려 사항들 (Other Considerations)**

- 엔드포인트 정의 (경로와 HTTP 메서드)
    - GET /api/restaurants
        - `lat`, `lon`, `range`, `sort`, `page`
        - sort - distance/rating

- 거리계산 방식 - MYSQL Point 타입 사용, Hibernate Spatial 함수 활용해서 쿼리 간소화
- "내 주변 맛집 보기", "특정 지역 주변 맛집 보기" 모두 중심 좌표를 파라미터로 하는 하나의 api로 처리

### **마일스톤 (Milestones)**

- **8월 29일**: ERD 확정 및 엔티티 구현
- **8월 30일 ~ 9월 2일**: 맛집 서비스 및 레파지토리, 컨트롤러 구현
- **9월 2일 ~ 9월 3일**: 테스트, 문서화 및 최종 점검

</details>

<details>
		<summary> 맛집 상세 정보 API </summary>

### **요약 (Summary)**

맛집 id를 받아서 맛집 상세 정보를 반환합니다.

### **목표 (Goals)**

- 맛집 고유 `id` 를 받아서 해당 맛집 상세정보를 반환합니다.
- 맛집의 `평가 항목`도 함께 반환합니다.
- `평가 항목` 에는 `총점, 리뷰 개수, 총점 평균`이 포함됩니다.
- 만약 조회하려는 맛집 정보가 `캐시`에 있으면 `캐시`에서 반환합니다.

### **계획 (Plan)**

#### API 응답 형식

  ```json
  [
  {
    "data": {
      "id": 1,
      "name": "밥집",
      "businessType": "KOREAN_FOOD",
      "phoneNumber": "010-1234-5678",
      "location": {
        "x": 1,
        "y": 1
      },
      "oldAddressFull": "용산구 청파동",
      "roadAddressFull": "용산구 이태원동",
      "reviewRating": {
        "totalReviews": 10,
        "totalScore": 30,
        "averageScore": 3.0
      }
    }
  }
]
  ```

#### 플로우 차트

```mermaid
graph TD
    A[맛집 ID 요청] --> B{맛집 존재 여부 확인}
    B --> |존재하지 않음| C[404 BAD_REQUEST 반환]
    B --> |존재함| D{캐시 확인}
    D --> |캐시에 있음| E[캐시에서 맛집 상세 정보 및 평가 반환]
    D --> |캐시에 없음| F[DB에서 맛집 상세 정보 및 평가 조회]
    F --> G{리뷰 개수 확인}
    G --> |10개 이상| H[캐시에 맛집 상세 정보 및 평가 저장]
    G --> |10개 미만| I[DB에서 맛집 상세 정보 및 평가 반환]
    H --> I

```

### **이외 고려 사항들 (Other Considerations)**

- 캐싱 조건
    - `리뷰가 10개 이상` 달린 맛집만 캐시에 저장합니다.

### **마일스톤 (Milestones)**

> ~8월 28일(수) : 요구 사항 분석
>
> ~8월 29일(목) : 맛집 상세 정보 반환 `dto` 생성, `더미 데이터` 생성
>
> ~8월 30일(금) : `controller. service` 로직 작성
>
> ~9월 3일(화) : `redis` 를 사용한 고도화 작업, `README` 작성, Rollout
>

</details>

## Skills

언어 및 프레임워크: `Java 17`, `Spring Boot 3.x.x`

데이터베이스: `MySQL`

배포: `AWS EC2`, `Github Actions`, `Docker`

ETC: `Swagger`, `Lombok`, `Junit`

협업툴: `GitHub`, `Discord`, `Notion`

## ERD

![Untitled (16)](https://github.com/user-attachments/assets/e4cc5680-2a01-4c3a-90eb-a61e3674c890)

## API 명세서

http://15.165.229.23:8080/swagger-ui/index.html

## 프로젝트 진행 및 이슈 관리

- 각 기능별 이슈 작성 후 하위 이슈 추가

<img width="1455" alt="스크린샷 2024-08-26 18 06 58 (1)" src="https://github.com/user-attachments/assets/2e487a73-f92e-413c-a85d-4de06553a20c">

## Authors

<table>
    <tr align="center">
        <td><B>주다애<B></td>
        <td><B>강경원<B></td>
        <td><B>이도은<B></td>
        <td><B>정의진<B></td>
        <td><B>조혜온<B></td>
        <td><B>이예림<B></td>
    </tr>
    <tr align="center">
            <td>
            <img src="https://github.com/jooda00.png?size=100">
            <br>
            <a href="https://github.com/jooda00"><I>jooda00</I></a>
        </td>
            <td>
            <img src="https://github.com/toughCircle.png?size=100">
            <br>
            <a href="https://github.com/toughCircle"><I>toughCircle<I></a>
        </td>
        <td>
            <img src="https://github.com/medoeun.png?size=100">
            <br>
            <a href="https://github.com/medoeun"><I>medoeun</I></a>
        </td>
        <td>
            <img src="https://github.com/uijin-j.png?size=100">
            <br>
            <a href="https://github.com/uijin-j"><I>uijin-j</I></a>
        </td>
        <td>
          <img src="https://github.com/hye-on.png?size=100">
            <br>
            <a href="https://github.com/hye-on"><I>hye-on</I></a>
        </td>
        <td>
          <img src="https://github.com/yerim123456.png?size=100">
            <br>
            <a href="https://github.com/yerim123456"><I>yerim123456</I></a>
        </td>
    </tr>

</table>
