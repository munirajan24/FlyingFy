package com.flyingfy.controller;


import com.flyingfy.dto.AuthRequests;
import com.flyingfy.model.User;
import com.flyingfy.security.JwtUtil;
import com.flyingfy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequests.Register req) {
        User u = userService.register(req.getEmail(), req.getPassword(), req.getFullName());
        return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequests.Login req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            String token = jwtUtil.generateToken(req.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}