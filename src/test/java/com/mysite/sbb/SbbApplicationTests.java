package com.mysite.sbb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

	@Autowired //QuestionRepository 인터페이스까지만 만들고, 스프링컨테이너에 rep로 등록안했는데 어떻게 주입이되지? - 하이버네이트라 가능한가?
	private QuestionRepository questionRepository;

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
		assertEquals(2,questions.size()); // 기대되는값, 컬렉션.size

		Question question = questions.get(0);
		String subject = question.getSubject();
		assertEquals("1번 질문", subject);
	}

	@Test
	void testFindById() {

		//id값으로 Question 엔티티 조회
		Optional<Question> question = this.questionRepository.findById(2);
		if(question.isPresent()) {
			Question realQues = question.get();
			assertEquals("신기하잖아!",realQues.getContent());

		}
	}

}
