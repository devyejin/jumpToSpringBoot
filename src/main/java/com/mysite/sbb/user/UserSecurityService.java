package com.mysite.sbb.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSecurityService implements UserDetailsService {

    //스프링 시큐리티가 로그인 인증 처리를 해주긴 하는데.
    //Repository는 회원정보 조회해서 던저주지 Controller는  스프링시큐리티가해주지
    //조회된 정보로 지지고 볶는건 Service의 일! 그리고, 스프링시큐리티가 제공해주는 기능으로 하니까
    //UserDetailsService 상속받아서 기능 이용

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);

        if(_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
            //여기서 사용자 없어서 EXP 터지고 Controler (스프링 시큐리티) -> 로그인페이지로 리다이렉트하며
            //리다이렉트 url에 error객체 담아보님
            //그러면 이 값이 넘어가니까! 에러문구로 여기서 정한 문구가 날라가겠지
        }

        SiteUser siteUser = _siteUser.get();
        //인가(권한)담을 용
        List<GrantedAuthority> authorities = new ArrayList<>();
        //여기서 권한활용하고 싶다면, DBMS User테이블에 권한관련 필드 추가하기
        if("admin".equals(siteUser.getUsername())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        //스프링에서 제공하는 User 객체에 이케이케 조회하는 유저 정보를 담아보내면
        //스프링 시큐리티가 로그인처리하며 확인하는데 이용 ( Controller의 post 추상화된 부분)
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);


    }
}
