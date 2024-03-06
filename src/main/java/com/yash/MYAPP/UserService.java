package com.yash.MYAPP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean matchPassword(UserAuthenticationDetails userAuthenticationDetails) {
        UserAuthenticationDetails user = userRepository.findByUsername(userAuthenticationDetails.getUsername());
        if(user == null) {
            return false;
        }
        return PasswordHashingUtil.verifyPassword(userAuthenticationDetails.getPassword(), user.getSalt(), user.getPassword());
    }
    public void createUser(String username, String password) {
        String salt = PasswordHashingUtil.generateSalt();
        String encodedPassword = PasswordHashingUtil.hashPassword(password, salt);
        UserAuthenticationDetails user = new UserAuthenticationDetails(username, encodedPassword, salt);
        userRepository.save(user);
    }
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public long getId(String username) {
        return userRepository.findByUsername(username).getId();
    }

    public String getUsername(long id) {
        return userRepository.getById(id).getUsername();
    }

}
