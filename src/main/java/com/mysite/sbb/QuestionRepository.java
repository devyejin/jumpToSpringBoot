package com.mysite.sbb;


import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<리포지토리대상이되는타입, PK속상타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
