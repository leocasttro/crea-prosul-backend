package org.br.prosul.creaprosulv1.config;

import jakarta.servlet.http.HttpServletResponse;
import org.br.prosul.creaprosulv1.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Removidas as importações de CorsConfiguration e UrlBasedCorsConfigurationSource,
// pois o WebConfig agora lida com o CORS.

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private final JwtAuthFilter jwtAuthFilter;
  @Autowired
  private final UserDetailsService userDetailsService;

  // O construtor permanece o mesmo
  public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // Desativa o CSRF para permitir requisições de outras origens, comum em APIs REST
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Removido o .cors() daqui, pois o WebConfig agora gerencia o CORS globalmente
            .authorizeHttpRequests(auth -> auth
                    // Permite acesso irrestrito aos endpoints de autenticação e registro de usuários
                    .requestMatchers("/api/auth/**").permitAll()
                    // Todas as outras requisições exigem autenticação
                    .anyRequest().authenticated())
            // Define a política de criação de sessão como STATELESS, ideal para APIs REST com JWT
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configuração de tratamento de exceções para requisições não autorizadas
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) -> {
                      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Define o status HTTP como 401
                      response.getWriter().write("Unauthorized: " + authException.getMessage()); // Envia uma mensagem de erro
                    }))
            // Adiciona o filtro JWT antes do filtro de autenticação de nome de usuário e senha do Spring Security
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build(); // Constrói e retorna a cadeia de filtros de segurança
  }

  // Removido o bean corsConfigurationSource(), pois o WebConfig agora lida com o CORS
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*")); // Ou especifique suas origens
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setExposedHeaders(List.of("Authorization"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Define o codificador de senhas a ser utilizado (BCrypt para segurança)
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    // Expõe o AuthenticationManager, necessário para a autenticação de usuários
    return authenticationConfiguration.getAuthenticationManager();
  }
}
