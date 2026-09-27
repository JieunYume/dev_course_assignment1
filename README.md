# 실행 방법
* **JDK 버전:** Java 21
* **Database:** H2 Database

### 데이터베이스(DB) 준비
* **H2 Database (내장형 DB 사용 시):**
    * 별도의 프로그램 설치가 필요 없으며, 프로젝트 실행 시 메모리에 DB가 자동으로 생성됩니다.

### 프로젝트 빌드 및 실행
프로젝트 루트 디렉토리로 이동한 뒤 아래 명령어를 입력합니다.
* **CMD 또는 PowerShell 사용 시:**
  ```cmd
  gradlew.bat clean bootRun
  
# API 명세
### 1. 할 일 생성
- HTTP Method: `POST` 
- Endpoint: `/api/v1/todos`
- Request Body (JSON):
  ```
  {
  "title": "데브코스 과제하기"
  }
  ```
- Response (성공 - 201 Created):
  ```
  {
    "resultCode": 201,
    "message": "할 일이 성공적으로 생성되었습니다.",
    "data": {
      "id": 1,
      "title": "데브코스 과제하기",
      "completionStatus": false,
      "createDate": "2026-09-28T00:36:18.7899815",
      "modifyDate": "2026-09-28T00:36:18.7899815"
    }
  }
  ```
- Response (실패 - 400 Bad Request): 제목이 비어 있거나 상한을 넘은 경우
  ```
  {
  "resultCode": 400,
  "message": "제목이 비어있습니다.",
  "data": null
  }
  ```

  ```
  {
  "resultCode": 400,
  "message": "제목의 길이는 80자 미만이어야 합니다.",
  "data": null
  }
  ```

### 2. 할 일 목록 조회

* **HTTP Method:** `GET`
* **Endpoint:** `/api/v1/todos`
* **Response (성공 - 200 OK):**
  ```json
  {
  "resultCode": 200,
  "message": "할 일 목록 조회에 성공했습니다.",
  "data": [
    {
      "id": 1,
      "title": "데브코스 과제하기",
      "completionStatus": false,
      "createDate": "2026-09-28T00:36:18.789982",
      "modifyDate": "2026-09-28T00:36:18.789982"
    },
    {
      "id": 2,
      "title": "헬스가기",
      "completionStatus": false,
      "createDate": "2026-09-28T01:07:21.423083",
      "modifyDate": "2026-09-28T01:07:21.423083"
    }
  ]
  }
  ```
  
### 3. 할 일 단건 조회

* **HTTP Method:** `GET`
* **Endpoint:** `/api/v1/todos/{id}`
* **Response (성공 - 200 OK):**
  ```json
  {
    "resultCode": 200,
    "message": "할 일 조회에 성공했습니다.",
    "data": {
      "id": 1,
      "title": "데브코스 과제하기",
      "completionStatus": false,
      "createDate": "2026-09-28T00:36:18.7899815",
      "modifyDate": "2026-09-28T00:36:18.7899815"
    }
  }
  
  ```


* **Response (실패 - 404 Not Found):** 존재하지 않는 ID를 조회할 경우
  ```json
  {
    "resultCode": 404,
    "message": "할 일 3번 항목 조회에 실패했습니다.",
    "data": null
  }
  ```

### 4. 할 일 수정 (제목 및 완료 여부 변경)

* **HTTP Method:** `PATCH`
* **Endpoint:** `/api/v1/todos/{id}`
* **Request Body (JSON):**
```json
{
  "title": "데브코스 과제 심화편 완료하기",
  "completionStatus": true
}

```


* **Response (성공 - 200 OK):**
```json
{
  "resultCode": 200,
  "message": "할 일이 성공적으로 수정되었습니다.",
  "data": {
    "id": 1,
    "title": "데브코스 과제 심화편 완료하기",
    "completed": true,
    "createDate": "2026-09-28T02:34:00.722565",
    "modifyDate": "2026-09-28T02:34:18.751102"
  }
}

```


* **Response (실패 - 404 Not Found):** 존재하지 않는 ID를 수정하려 할 경우
```json
{
  "resultCode": 404,
  "message": "할 일 2번 항목 조회에 실패했습니다.",
  "data": null
}

```

### 5. 할 일 삭제

* **HTTP Method:** `DELETE`
* **Endpoint:** `/api/v1/todos/{id}`
* **Response (성공 - 200 OK):**
```json
{
  "resultCode": 200,
  "message": "할 일이 성공적으로 삭제되었습니다.",
  "data": null
}

```


* **Response (실패 - 404 Not Found):** 존재하지 않는 ID를 삭제하려 할 경우
```json
{
  "resultCode": 404,
  "message": "해당하는 할 일을 찾을 수 없습니다. (id: 999)",
  "data": null
}

```
# 설계 설명
### 응답 본문 구조
```
{
  "status": 200,
  "message": "요청이 성공했습니다.",
  "data": {
    "id": 1,
    "title": "스프링 공부하기"
  }
}
```
- 상태 코드와 메시지 필드를 통해 오류 피드백을 전달하면, 클라이언트가 상황을 명확하게 인지할 수 있다.

### 완료 여부 수정의 2가지 방식
1. 별도 API로 두는 방식
   - 장점: 완료 여부만 수정하기 때문에 전송 데이터가 작다.
   - 단점1: 사용자가 제목을 수정하면서 완료 여부도 수정할 경우, 요청 횟수가 늘어난다.
   - 단점2: 만약 완료를 눌렀을 때 완료한 시각을 기록해야 한다는 요구사항이 생긴다면, 결국 수정 API와 비슷한 로직을 중복 구현해야 한다.
2. 수정 API에 포함하는 방식
   - 장점: 하나의 API로 처리할 수 있어서 클라이언트 개발이 단순하고, 하나의 엔드포인트만 관리하면 돼서 개발 속도가 빨라진다.
   - 단점: 전송 데이터가 상대적으로 크다.
- => 유지보수 측면에서 2번째 방식이 관리하기 편리하고, 추후 완료와 함께 다른 로직이 추가되면 중복 코드가 발생할 수 있기에 수정 API에 포함하는 방식으로 결정했다.

### 주소와 상태 코드 설계 이유
* REST API 표준을 준수했다. 주소에는 동사, 예를 들어 `create`, `get`, `update`를 포함하지 않고, 자원을 나타내는 **명사** 위주로 구성했다.
* HTTP 메서드를 분리하여 엔드포인트를 최소화했다. HTTP Method (`POST`, `GET`, `PATCH`, `DELETE`)를 통해 행위를 표현하여, API 엔드포인트의 개수를 최소화하고 일관성과 가독성을 높였다.
    * 예: `POST /api/v1/todos` (생성), `GET /api/v1/todos` (목록 조회) 등

* 클라이언트가 요청의 성공/실패 여부를 직관적으로 파악할 수 있도록 표준 HTTP 상태 코드를 적절히 매핑했다.
    * **200 OK:** 조회 및 수정, 삭제 등 요청이 성공적으로 처리되었을 때
    * **201 Created:** 새로운 할 일 자원이 성공적으로 생성(`POST`)되었을 때
    * **400 Bad Request:** 잘못된 요청 데이터(유효성 검증 실패 등)가 들어왔을 때
    * **404 Not Found:** 요청한 리소스(ID 등)가 존재하지 않을 때

### DB로 h2를 고른 이유
* 빠른 개발 및 학습이 목적이기 때문이다. 프로젝트를 개발하고 테스트하는 과정에서 별도의 DBMS 설치 및 복잡한 설정(네트워크, 계정 등)으로 인한 오버헤드를 줄이고자 했다.
* h2는 내장형(In-Memory)을 지원한다. 애플리케이션 실행과 동시에 가볍게 동작하며, 테스트 환경 구축이 매우 용이하여 초기 기능 구현에 집중하기 위해 H2를 선택했다.

# 실행 결과
- 요청은 test-request.http 파일 참고
- 응답(순서대로)
  ```json
    {
      "resultCode": 201,
      "message": "할 일이 성공적으로 생성되었습니다.",
      "data": {
        "id": 1,
        "title": "스프링 과제 제출하기",
        "completed": false,
        "createDate": "2026-09-28T02:53:59.0023897",
        "modifyDate": "2026-09-28T02:53:59.0023897"
      }
    }
  ```

  ```json
    {
      "resultCode": 200,
      "message": "할 일 목록 조회에 성공했습니다.",
      "data": [
        {
          "id": 1,
          "title": "스프링 과제 제출하기",
          "completed": false,
          "createDate": "2026-09-28T02:53:59.00239",
          "modifyDate": "2026-09-28T02:53:59.00239"
        }
      ]
    }
  ```

  ```json
    {
      "resultCode": 200,
      "message": "할 일이 성공적으로 수정되었습니다.",
      "data": {
        "id": 1,
        "title": "스프링 과제 제출하기",
        "completed": true,
        "createDate": "2026-09-28T03:07:15.713014",
        "modifyDate": "2026-09-28T03:07:15.713014"
      }
    }
  ```

  ```json
    {
      "resultCode": 200,
      "message": "할 일이 성공적으로 삭제되었습니다.",
      "data": null
    }
  ```

    ```json
    {
      "resultCode": 400,
      "message": "제목이 비어있습니다.",
      "data": null
    }
    ```

    ```json
    {
      "resultCode": 404,
      "message": "할 일 999번 항목 조회에 실패했습니다.",
      "data": null
    }
    ```