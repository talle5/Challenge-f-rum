package challenge.forum.fforwm.security;

import challenge.forum.fforwm.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtEncoder encoder;

    public Jwt generateToken(User auth) {
        var now = Instant.now();
        var expire = 7200L;
        var scopes = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuedAt(now)
//                .expiresAt(now.plusSeconds(expire))
                .subject(auth.getId().toString())
                .claim("scope",scopes)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims));
    }
}
