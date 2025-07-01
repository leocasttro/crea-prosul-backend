package org.br.prosul.creaprosulv1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
  }

  @Test
  public void testLogin_Success_GeneratesToken() throws Exception {
    // Configura o mock do UserRepository
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

    // Cria o JSON de requisição
    String loginRequest = objectMapper.writeValueAsString(
            new LoginRequest("testuser", "password123")
    );

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginRequest))
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
    String loginRequest = objectMapper.writeValueAsString(
            new LoginRequest("testuser", "wrongpassword")
    );

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginRequest))
            .andExpect(status().isUnauthorized());

    // Verifica se o UserRepository foi chamado
    verify(userRepository, times(1)).findByUsername("testuser");
  }

  @Test
  public void testLogin_UserNotFound_Fails() throws Exception {
    // Configura o mock para retornar Optional vazio
    when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

    // Cria o JSON de requisição
    String loginRequest = objectMapper.writeValueAsString(
            new LoginRequest("unknown", "password123")
    );

    // Executa a requisição POST para o endpoint de login
    mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginRequest))
            .andExpect(status().isUnauthorized());

    // Verifica se o UserRepository foi chamado
    verify(userRepository, times(1)).findByUsername("unknown");
  }

  // Classe auxiliar para o JSON de requisição
  private static class LoginRequest {
    public String username;
    public String password;

    public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }
}