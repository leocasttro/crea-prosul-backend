package org.br.prosul.creaprosulv1.config;

import org.br.prosul.creaprosulv1.entity.UserEntity;
import org.br.prosul.creaprosulv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdminUserInitializer implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (userRepository.findByUsername("admin").isEmpty()) {
      UserEntity admin = new UserEntity();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin123"));
      admin.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
      userRepository.save(admin);
      System.out.println("Usuário administrador criado com sucesso!");
    } else {
      System.out.println("Usuário administrador já existe.");
    }
  }
}