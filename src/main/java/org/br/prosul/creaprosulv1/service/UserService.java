package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.UserCreateDTO;
import org.br.prosul.creaprosulv1.entity.UserEntity;
import org.br.prosul.creaprosulv1.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ResponseEntity<?> createUser(UserCreateDTO userCreateDTO, Authentication authentication) {

    System.out.println(authentication.getAuthorities());
    boolean isAdmin = authentication.getAuthorities()
            .stream().anyMatch(grandedAuthority -> grandedAuthority
                    .getAuthority().equals("ROLE_ADMIN"));
    if (!isAdmin) {
      return ResponseEntity.status(403).body("Apenas administrador pode criar usuários");
    }

    UserEntity newUser = new UserEntity();
    newUser.setUsername(userCreateDTO.getUsername());
    newUser.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
    newUser.setRoles(userCreateDTO.getRoles());

    userRepository.save(newUser);

    return ResponseEntity.ok("usuário criado com sucesso!");
  }
}
