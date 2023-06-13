package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Long> { //인터페이스가 인터페이스 상속받을때는 extends (즉, 상속은 extends, 구현만 implements)
    //JpaRepository<Entity,PK>

}
