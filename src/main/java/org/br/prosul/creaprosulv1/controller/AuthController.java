package org.br.prosul.creaprosulv1.controller;

import org.br.prosul.creaprosulv1.model.UserModel;
import org.br.prosul.creaprosulv1.service.JwtService;
import org.br.prosul.creaprosulv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService userService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
    );

    String token = jwtService.generateToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserModel userModel = userService.findByUsername(userDetails.getUsername());

    if (userModel == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("user", userModel);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
      Map<String, String> error = new HashMap<>();
      error.put("message", "Usuário não autorizado");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserModel user = userService.findByUsername(userDetails.getUsername());
    if (user == null) {
      Map<String, String> error = new HashMap<>();
      error.put("message", "Usuário não encontrado");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    return ResponseEntity.ok(user);
  }

  public static class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
  }
}