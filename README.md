

POST /api/events : 리뷰에 대한 이벤트 점수 부여 API

#### SPECIFICATIONS

```
POST /events
{
"type": "REVIEW",
"action": "ADD", /* "MOD", "DELETE" */
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "좋아요!",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```

GET /api/events/{userId} : userId에 해당하는 이벤트 점수를 보여주는 API

GET /api/events/{userId}/score_history : userId에 해당하는 포인트 이력 조회 API



---

### DUMMY DATA

USER (userId )

- c2a95f50-d3a2-44d0-a7d8-d7861263c669
- e2fbef4b-3395-451d-a93f-394deb87380a
- 3ede0ef2-92b7-4817-a5f3-0c575361f745



PLACE (placeId)

- b31d2b93-7352-4f63-9e7d-80c0346ca427
- 6101b657-93ab-4b08-b2ee-d2eddc26b6bd
- 2e4baf1c-5acb-4efb-a1af-eddada31b00f



userId, placeId에 더미 데이터를 넣어주세요



---

### 고려사항

- 밸류 타입

  - 명확하게 표현하기 위해서 밸류 오브젝트를 사용하였습니다.

  - ReviewScore를 통해 텍스트 점수, 이미지 점수, 첫 리뷰 점수를 묶어 표현하였습니다.

  - Score 밸류 타입을 통해 점수를 명확하게 표현하였고, 단순 Long타입의 계산이 아닌 Score의 메소드를 사용해 명확하게 점수 계산을 표현하였습니다.

- 애그리거트 키로 참조
  - 애그리거트를 직접 참조하게 된다면
    - 다른 애그리거트의 상태를 변경 가능해진다. -> 애그리거트 간의 의존 결합도를 높여 변경을 어렵게 만든다.
  - 위의 문제를 해결하기 위해 ID 참조
    - 애그리거트의 경계가 명확해지고 애그리거트 간 물리적 연결을 제거해 모델의 복잡도를 낮춘다.
    - 애그리거트 간의 의존을 제거한다.

- 응용 서비스의 크기
  - 한 도메인에 관련된 기능을 구현한 코드
    - 한 클래스에 위치하므로 각 기능에서 동일 로직에 대한 중복 제거할 수 있다.
    - 한 서비스 클래스의 코드 크기가 커지면 연관성이 적은 코드가 한 클래스에 함께 위치할 가능성이 높아지고 결과적으로 관련 없는 코드가 뒤섞여 코드를 이해하는데 방해가 된다.
  - 구분되는 기능별로 서비스 클래스를 구현하는 방식
    - 각 클래스 별로 필요한 의존 객체만 포함하므로 다른 기능을 구현한 코드에 영향을 받지 않는다.

- MySQL에서 PK에 대해 기본적으로 인덱스를 만들어 따로 인덱스를 만들지 않았습니다.

- Service에서 ReviewEvent의 PK가 아닌 값인 reviewId로 조회하므로 이에 대한 인덱스를 만들었습니다.

  