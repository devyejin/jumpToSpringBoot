package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SbbApplicationTests {

	@Autowired //QuestionRepository 인터페이스까지만 만들고, 스프링컨테이너에 rep로 등록안했는데 어떻게 주입이되지? - 하이버네이트라 가능한가?
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void signUpTest() {
		SiteUser user = new SiteUser();
		user.setUsername("test user");
		user.setPassword("1234");
		user.setEmail("222@naver.com");
		this.userRepository.save(user);
	}


//	@Test
	void testJpa() {

		Question q1 = new Question();
		q1.setSubject("1번 질문");
		q1.setContent("rep를 스프링컨테이너에 등록없이도 di주입이 가능하다고?" +
				"그리고, 쿼리대신 Entity로 쿼리를 넣는다고?!");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); //첫 번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("2번 질문");
		q2.setContent("신기하잖아!");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

	}

	@Test
	void testFinadAll() {
		List<Question> questions = this.questionRepository.findAll();
		assertEquals(1,questions.size()); // 기대되는값, 컬렉션.size

//		Question question = questions.get(0);
//		String subject = question.getSubject();
//		assertEquals("1번 질문", subject);
	}

	@Test
	void testFindById() {

		//id값으로 Question 엔티티 조회
		Optional<Question> question = this.questionRepository.findById(2);
		if(question.isPresent()) {
			Question realQues = question.get();
			assertEquals("신기하잖아!",realQues.getContent());

		} //Optional을 이용해서 null 값이면 가져오지않아서 NPE 발생하는걸 방지
	}

	@Test
	void testFindBySubject() {
		// JPA에서 제공하는 JpaRepository에서는 findById메서드만 제공
		Question bySubect = this.questionRepository.findBySubject("2번 질문");
		assertEquals(2, bySubect.getId());

	}

	@Test
	void testFindBySubjectAndContent() {
		Question bySubjectAndContent = this.questionRepository.findBySubjectAndContent("2번 질문", "신기하잖아!");
		assertEquals(2,bySubjectAndContent.getId());
	}

	@Test
	void testFindBySubjectLike() {
		List<Question> questions = this.questionRepository.findBySubjectLike("%질문%");
//		questions.stream()
//				.forEach(question ->
//						System.out.println(question.getSubject()));
		Question question = questions.get(0);
//		assertEquals("1번 질문",question.getSubject());

		assertThat(question.getSubject())
				.isEqualTo("1번 질문");
	}

	//데이터 수정하는 테스트코드
	@Test
	void testUpdate() {

		//given
		Optional<Question> byId = this.questionRepository.findById(1);

		//when
		if(byId.isPresent()) { //Optional을 이용해서 값이 들었는지 확인하는 방법으로 로직 수행
			Question question = byId.get();
			question.setSubject("1번 질문에 질문 제목을 수정했어");
			this.questionRepository.save(question);
		}

		//then
		Optional<Question> changedSubject =this.questionRepository.findById(1);
		assertTrue(changedSubject.isPresent()); //assertTrue() : true인지 테스트
		assertThat(changedSubject.get().getSubject()).isEqualTo("1번 질문에 질문 제목을 수정했어");


	}

	@Test
	void testDelete() {
		//given
		assertThat(this.questionRepository.count()).isEqualTo(2);
		Optional<Question> byId = this.questionRepository.findById(1);

		//when
		if(byId.isPresent()) {
			Question question = byId.get();
			this.questionRepository.delete(question);
		}

		//then
		assertThat(this.questionRepository.count()).isEqualTo(1);
	}

	//Answer Test!
	@Test
	void testfindByIdAns() {
		Optional<Question> byId = this.questionRepository.findById(2);
		assertTrue(byId.isPresent()); //반환타입은 void지만 테스트코드에서 사용됨
		Question question = byId.get();

		Answer ans = new Answer();
		ans.setContent("답변 컨텐츠");
		ans.setQuestion(question); //어떤 질문의 답변인지 Question 객체 필요
		ans.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(ans);
	}

	//답변에 연결된 질문 찾기 - 답변테이블 Question 속성 이용
	@Test
	void testCheckAns() {
		Optional<Answer> byId = this.answerRepository.findById(1);//2번 질문이랑은 question 칼럼으로 연결됐고, 답변자체 번호는 1임 (ManyToOne)관계니까 PK가 다른건 당연!
		assertTrue(byId.isPresent());
		Answer answer = byId.get();
		assertThat(answer.getQuestion().getId()).isEqualTo(2);

	}

	//질문에 달린 답변 찾기 - 질문 Entitiy에 정의한 answerList 이용
	@Transactional //트랜잭션을 걸어서 DB세션을 유지시켜준다.
	@Test
	void test() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		List<Answer> answerList = question.getAnswerList(); //DB테이블에는  answerList 데이터가 없는데, 자바 객체(Entity)로 변환하고 나면 알아서 맵핑이 됨 , 이게 하이버네이트 기술?!

		assertEquals(1, answerList.size());
		assertEquals("답변 컨텐츠",answerList.get(0).getContent());
	}

	@Test
	void makeDummyData() {
		for(int i=0; i<300; i++) {
			String subject = String.format("테스트 데이터입니다 : [%03d]", i);
			String content = "내용은 무슨 내용이 좋을까?" +
					"강아지가 예뻐요?" +
					"고양이가 예뻐요?" +
					"사실 강아지가 최고입니다. 그 중 단연 푸들이 최고죠!";
			this.questionService.create(subject, content);

		}
	}

}
