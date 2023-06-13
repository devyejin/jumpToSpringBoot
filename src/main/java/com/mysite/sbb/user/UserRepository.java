package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Long> { //인터페이스가 인터페이스 상속받을때는 extends (즉, 상속은 extends, 구현만 implements)
    //JpaRepository<Entity,PK>

    //pk인 findById는 기본지원이지만 pk아닌 칼럼 커리문은 없으므로 명시해줘야함
    Optional<SiteUser> findByusername(String username);

}
