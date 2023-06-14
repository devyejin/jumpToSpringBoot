package com.mysite.sbb.user;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import javax.persistence.*;


@Getter
@Setter
@Entity
public class SiteUser { //회원정보 저장하는 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;
    private String password;

    @Column(unique = true)
    private String email;

}
