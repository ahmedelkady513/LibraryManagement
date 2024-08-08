package cc.maids.librarymanagement.config.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Environment environment;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
        return claims.getPayload().getSubject();
    }

    public boolean validateToken(String token) throws JwtException {
        Claims claims = extractAllClaims(token);
        return true;
    }

    public String generateToken(String username) {
        return createToken(null, username);
    }

    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(Date.from(Instant.now().plusMillis(environment.getProperty("token.expiration", Long.class, 3600000L))))
                .issuedAt(Date.from(Instant.now()))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Objects.requireNonNull(this.environment.getProperty("token.secret")).getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
