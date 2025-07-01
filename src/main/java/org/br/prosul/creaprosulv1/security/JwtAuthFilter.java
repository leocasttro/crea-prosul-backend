package org.br.prosul.creaprosulv1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    System.out.println("JwtAuthFilter executando para: " + requestURI);

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = authHeader.substring(7);
      System.out.println("Token recebido: " + token);

      Claims claims = Jwts.parserBuilder()
              .setSigningKey(getSigningKey())
              .build()
              .parseClaimsJws(token)
              .getBody();

      String username = claims.getSubject();
      System.out.println("Username extraído: " + username);

      @SuppressWarnings("unchecked")
      List<String> roles = claims.get("roles", List.class);
      System.out.println("Roles extraídas: " + roles);

      if (username == null) {
        throw new RuntimeException("Username não encontrado no token");
      }

      List<SimpleGrantedAuthority> authorities = roles.stream()
              .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                      username,
                      null,
                      authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
      System.out.println("Autenticação configurada para: " + authentication);

    } catch (Exception e) {
      System.err.println("Erro na autenticação JWT: " + e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Falha na autenticação: " + e.getMessage());
      return;
    }

    filterChain.doFilter(request, response);
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }
}