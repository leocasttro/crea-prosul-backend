package org.br.prosul.creaprosulv1.controller;

import org.br.prosul.creaprosulv1.dto.UserCreateDTO;
import org.br.prosul.creaprosulv1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/createUser")
  public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO,
                                      Authentication authentication) {
    return userService.createUser(userCreateDTO, authentication);
  }
}
