package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository; //final 주의!
    private final PasswordEncoder passwordEncoder;
    
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //스프링 시큐리티 지원 기능
//        //BCrpyt니까 이진수로 해준다는거 아닐까?(추측) -> 아님 -> BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
//        user.setPassword(passwordEncoder.encode(password));


        //encorder를 new연산자로 생성해서 사용하는것보다  '스프링빈'으로 등록하고 사용하는게 효율적임, encorder 변경발생시 사용한 곳 코드를 다 수정해야함
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
