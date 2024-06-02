package dev.leonwong.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    /*
    In the case where the secret key is not Base64 encoded, we can invoke the getByte() method on the plain string:
    However, this is not recommended because the secret may be poorly formed, and the string may contain non-UTF-8 characters. Hence, we must ensure the key string is Base64 encoded before generating a secret key from it.
     https://www.baeldung.com/spring-security-sign-jwt-token
     */
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email",auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String token) {
        token = token.substring(7);

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("email", String.class);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();

        for(GrantedAuthority authority : authorities) {
            auth.add(authority.getAuthority());
        }

        return String.join(",", auth);
    }
}
