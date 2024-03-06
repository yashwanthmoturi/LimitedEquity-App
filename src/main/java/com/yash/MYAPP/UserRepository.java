package com.yash.MYAPP;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAuthenticationDetails, Long> {
    UserAuthenticationDetails findByUsername(String username);
}
