package Web2.Tp1.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String secret;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {

    String header = req.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      try {
        String token = header.replace("Bearer ", "");
        var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        Object idUsuario = claims.get("id");
        String rol = claims.get("rol", String.class);

        var auth = new UsernamePasswordAuthenticationToken(
            idUsuario, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol)));

        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception ignored) {
        SecurityContextHolder.clearContext();
      }
    }

    chain.doFilter(req, res);
  }
}
