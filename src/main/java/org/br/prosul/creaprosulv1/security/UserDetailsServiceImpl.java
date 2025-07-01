package org.br.prosul.creaprosulv1.security;

import org.br.prosul.creaprosulv1.repository.UserRepository;
import org.br.prosul.creaprosulv1.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

    // Remove the "ROLE_" prefix from roles
    String[] roles = userEntity.getRoles().stream()
            .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
            .toArray(String[]::new);

    return User.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .roles(roles)
            .build();
  }
}