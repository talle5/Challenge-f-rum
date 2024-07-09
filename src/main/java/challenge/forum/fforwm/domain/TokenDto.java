package challenge.forum.fforwm.domain;

import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

public record TokenDto(String token, Instant expireTime) {
    public TokenDto(Jwt jwtToken) {
        this(jwtToken.getTokenValue(),jwtToken.getExpiresAt());
    }
}
