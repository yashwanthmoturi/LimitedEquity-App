package com.yash.MYAPP;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserAuthenticationDetails, Long> {
    UserAuthenticationDetails findByUsername(String username);
}
