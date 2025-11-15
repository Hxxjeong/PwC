## 삼일PwC엑셀러레이션센터 과제

---

- Swagger 주소: http://hxxzz.duckdns.org/swagger-ui/index.html

## 구현 내용
- 로그인 사용자 Todo 생성/조회/수정/삭제
  - 사용자 로그인의 경우 토큰값만 발급받을 정도로만 구현함
  - 등록/수정/삭제의 경우 로그인 유저만 가능하도록 구현
  - 수정/삭제의 경우 본인의 글이 아니면 예외 처리
    - 관리자의 수정/삭제 정책의 경우 예외 처리 조건 추가
    - 관리자는 삭제된 항목 포함 모든 글 조회 가능
- 반환 시 작성자, 생성일, 마감일, D-Day 반환
- Todo 등록/수정 시 태그 여러 개 사용 가능
- 수정 시 Todo 항목 변경 가능
  - 변경하려는 항목이나 타겟 항목이 삭제된 것이면 예외
  - 타겟 순서로 변경 시 하나씩 밀리도록 구현
- 조회의 경우 필터링과 전체(삭제되지 않은 항목) 항목 통합해서 구현
  - 차후에 삭제한 Todo를 되돌리는 요구사항을 고려하여 soft delete로 구현

** `application.yml`은 push 하지 않음

```markdown
등록되어 있는 사용자 정보

- user1
nickname: user1
password: 0000

- user2
nickname: user2
password: 0000

- admin (관리자)
nickname: admin
password: admin
```