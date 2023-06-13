#JumpToSpringBoot
---

- 위키독스 JumpToSpringBoot를 기반으로 게시판 프로젝트를 연습
- 테스트 코드 등 일부 코드는 변형된 로직들이 있습니다.



## 답변 등록 후 출력
- th:if="${#lists.size(question.answerList) ge 1 }" 을 이용해서 답변이 존재하는 경우에만 렌더링 되도록 변경


## 페이징 처리 보완할 점
- 5미만 페이지일 경우, 페이지 이동 버튼이 10개가 아닌 유동적으로 나타는 것
- 뒷 부분도 남은 페이지가 5개 미만일 때 유동적인 부분


## 회원가입 처리
### url 작성시 주의할 점
- "redirect: /" 이렇게 공백스페이스가 들어가니까 %20 값으로 들어가는 문제가 발생함
- 공백을 주의하자

## unique 제약조건(username, email) Exception 발생 처리
- DataIntegrityViolationException
- client 측 에러이지만, server(500) 에러가 발생하게됨
- 이는 서버측에서 처리할 에러가 아니기 때문에 클라이언트단으로 던져줘야함
- DBMS에서 unique 제약조건 위반 예외 -> Repository로 -> 
- 추후 AJAX 공부해야 실시간으로 ID, EMAIL 가용성여부 알려주는 기능 추가구현하기


## 완성후 추가 할 로직
- ADMIN, USER 권한에 따른 기능 제어 구현