package com.yash.MYAPP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthenticationDetails loginRequest) {
        if(! userService.matchPassword(loginRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Wrong Credentials");
        }
        // Generate JWT token upon successful authentication
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        long id = userService.getId(loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse(token, id);
//        loginResponse.setToken(token);
//        loginResponse.setId(id);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAuthenticationDetails registerRequest) {

        try {
            if(userService.userExists(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Failed to register user because username already exist");
            }
            userService.createUser(registerRequest.getUsername(), registerRequest.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register user: " + e.getMessage());
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody TokenValidationRequest request) {
        String token = request.getToken();
        long id = request.getId();
        String username = userService.getUsername(id);

        if (username == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (jwtUtil.validateToken(token, username)) {
            return ResponseEntity.ok("Token is valid for user: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid for user: " + username);
        }
    }



}

class LoginRequest {
    private String username;
    private String password;
    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class LoginResponse {
    private String token;

    private long id;

    public LoginResponse(String token, long id) {
        this.token = token;
        this.id = id;
    }

    //    public LoginResponse(String token) {
//        this.token = token;
//    }
    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }
// Constructor, getters, and setters
}

class TokenValidationRequest {
    private String token;
    private long id;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
