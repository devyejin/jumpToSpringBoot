package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository; //final 주의!
    
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsrname(username);
        user.setEmail(email);
        //pwd의 경우 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //스프링 시큐리티 지원 기능
        //BCrpyt니까 이진수로 해준다는거 아닐까?(추측) -> 아님 -> BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
