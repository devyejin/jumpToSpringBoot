package com.mysite.sbb.user;

import lombok.Getter;

@Getter //불변 상수 자료형이므로 Setter는 필요 없음
public enum UserRole { //열거형(enum) 이용
    ADMIN("ROLE_ADMIN"), //ADMIN은 "ROLE_ADMIN"라는 값을 가짐
    USER("ROME_USER");

    private String value;

    //생성자
    UserRole(String value) {
        this.value = value;
    }

}
