package org.br.prosul.creaprosulv1.security;

import org.br.prosul.creaprosulv1.entity.UserEntity;
import org.br.prosul.creaprosulv1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  private UserEntity userEntity;

  @BeforeEach
  public void setUp() {
    // Configura um UserEntity mockado
    userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setUsername("testuser");
    userEntity.setPassword("encodedPassword");
    userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
  }

  @Test
  public void testLoadUserByUsername_Success() {
    // Configura o mock do UserRepository para retornar o userEntity
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

    // Executa o método a ser testado
    UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

    // Verifica os resultados
    assertNotNull(userDetails);
    assertEquals("testuser", userDetails.getUsername());
    assertEquals("encodedPassword", userDetails.getPassword());
    assertTrue(userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    assertTrue(userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));

    // Verifica se o método do repositório foi chamado
    verify(userRepository, times(1)).findByUsername("testuser");
  }

  @Test
  public void testLoadUserByUsername_UserNotFound() {
    // Configura o mock do UserRepository para retornar Optional vazio
    when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

    // Verifica se a exceção UsernameNotFoundException é lançada
    Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername("unknown");
    });

    // Verifica a mensagem da exceção
    assertEquals("Usuário não encontrado: unknown", exception.getMessage());

    // Verifica se o método do repositório foi chamado
    verify(userRepository, times(1)).findByUsername("unknown");
  }
}