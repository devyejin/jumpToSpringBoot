package com.mysite.sbb.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.xml.transform.sax.SAXResult;

@Setter
@Getter
@Entity
public class SiteUser { //스프링 시큐리티에 User 클래스가 존재해서 혼동방지를 위해

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA, DB에서 PK 자동 생성되도록
    private Long id;

    @Column(unique = true) // 칼럼 제약조건 unique
    private  String username;
    private  String password;

    @Column(unique = true) // 칼럼 제약조건 unique
    private String email;
}
