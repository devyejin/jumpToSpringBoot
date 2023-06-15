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

## 보완해야할 부분
- nav 상단에 로그인한 사용자명+환영합니다 출력
- 메인페이지의 경우, 아래 로직을 사용
  - if(principal != null) {
    SiteUser  user = this.userService.getUser(principal.getName());
    model.addAttribute("loginUsername",user.getUsername());
    }
- nav는 공통템플릿인데, url이 변경되면 이 데이터가 없기 때문에 url마다 반복적으로 코드를 넣어줘야함
- 좋은 해결방법이 없을까? 서버에 세션객체를 만들고, 그 세션객체를 넘기나? AOP를 적용하나?
- 타임리프의 #authentication 객체를 이용하면 더 간단함. Controller에서 Principal객체를 넘겨주지 않아도
  타임리프 템플릿에서 Principal 객체를 내부적으로 참조해줌..