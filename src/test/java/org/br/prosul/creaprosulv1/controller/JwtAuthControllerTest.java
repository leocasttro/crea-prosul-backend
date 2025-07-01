package org.br.prosul.creaprosulv1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.br.prosul.creaprosulv1.controller.AuthController;
import org.br.prosul.creaprosulv1.entity.UserEntity;
import org.br.prosul.creaprosulv1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private UserEntity userEntity;
  private BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    passwordEncoder = new BCryptPasswordEncoder();
    userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setUsername("testuser");
    userEntity.setPassword(passwordEncoder.encode("password123"));
    userEntity.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN")); // Inclui prefixo ROLE_
  }

  @Test
  public void testLogin_Success_GeneratesToken() throws Exception {
    // Configura o mock do UserRepository
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

    // Cria o JSON de requisição
    AuthController.LoginRequest loginRequest = new AuthController.LoginRequest();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("password123");

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token").isString());

    // Verifica se o UserRepository foi chamado
    verify(userRepository, times(1)).findByUsername("testuser");
  }

  @Test
  public void testLogin_InvalidCredentials_Fails() throws Exception {
    // Configura o mock para retornar usuário válido
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

    // Cria o JSON de requisição com senha incorreta
    AuthController.LoginRequest loginRequest = new AuthController.LoginRequest();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("wrongpassword");

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isUnauthorized());

    // Verifica se o UserRepository foi chamado
    verify(userRepository, times(1)).findByUsername("testuser");
  }

  @Test
  public void testLogin_UserNotFound_Fails() throws Exception {
    // Configura o mock para retornar Optional vazio
    when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

    // Cria o JSON de requisição
    AuthController.LoginRequest loginRequest = new AuthController.LoginRequest();
    loginRequest.setUsername("unknown");
    loginRequest.setPassword("password123");

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isUnauthorized());

    // Verifica se o UserRepository foi chamado
    verify(userRepository, times(1)).findByUsername("unknown");
  }
}
