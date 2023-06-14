package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole { //사용자 권한 부여. 상수보다는 ENUM을 사용하자!
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
    UserRole(String value) {
        this.value = value;
    }
}
