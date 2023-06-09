#JumpToSpringBoot
---

- 위키독스 JumpToSpringBoot를 기반으로 게시판 프로젝트를 연습
- 테스트 코드 등 일부 코드는 변형된 로직들이 있습니다.



## 답변 등록 후 출력
- th:if="${#lists.size(question.answerList) ge 1 }" 을 이용해서 답변이 존재하는 경우에만 렌더링 되도록 변경
