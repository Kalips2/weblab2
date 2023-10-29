package com.example.weblab2.services.impl.security;

import com.example.weblab2.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
  @Value("${jwt.secret.key}")
  private String secretKey;

  @Override
  public String getTokenFromRequest(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }

    return null;
  }

  @Override
  public boolean validateJwtToken(String token) {
    final Claims claims = getClaimsFromToken(token);
    final Date expirationDate = claims.getExpiration();
    return !expirationDate.before(new Date());
  }

  @Override
  public String getEmailFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

//  @Override
//  public String generateToken(UserDetails userDetails) {
//    Map<String, Object> map = new HashMap<>();
//    map.put("roles", userDetails.getAuthorities());
//    return Jwts
//        .builder()
//        .setClaims(map)
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//        .signWith(getKey(), SignatureAlgorithm.HS512)
//        .compact();
//  }

  @Override
  public String generateToken(Collection<? extends GrantedAuthority> roles, String username) {
    Map<String, Object> map = new HashMap<>();
    map.put("roles",roles);
    return Jwts
        .builder()
        .setClaims(map)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
        .signWith(getKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  private Claims getClaimsFromToken(String token) {
    final Key key = getKey();
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getKey() {
    final byte[] array = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(array);
  }
}
