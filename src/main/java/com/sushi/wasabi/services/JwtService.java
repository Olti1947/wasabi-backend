package com.sushi.wasabi.services;

import com.sushi.wasabi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
private final UserDetailsService userDetailsService;

@Value("${jwt.secretKey}")
private String SECRET_KEY;

public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
}

public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
}

public String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);
}

public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails){
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("roles",userDetails.getAuthorities())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}

public Boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}

private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
}

private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
}

private Key getSignInKey(){
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
}

public String generateRefresh(Map<String, Objects> extraClaims, UserDetails userDetails){
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 604800000))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}

public String getEmailFromToken(String token){
    return extractUserName(token);
}

public Boolean validateToken(String token){
    String userEmail = extractUserName(token);
    if (!StringUtils.isEmpty(userEmail) && !isTokenExpired(token)) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        return isTokenValid(token, userDetails);
    }
    return false;
}

public Integer getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
        throw new IllegalStateException("No authenticated user");
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof User user) {
        return user.getId();
    }

    throw new IllegalStateException("Authenticated principal is not User");
}

}
