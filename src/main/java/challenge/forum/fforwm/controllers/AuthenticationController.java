package challenge.forum.fforwm.controllers;

import challenge.forum.fforwm.domain.TokenDto;
import challenge.forum.fforwm.domain.user.User;
import challenge.forum.fforwm.domain.user.dto.Register;
import challenge.forum.fforwm.domain.user.dto.Login;
import challenge.forum.fforwm.security.JwtService;
import challenge.forum.fforwm.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Login login) {
        var a = new UsernamePasswordAuthenticationToken(login.login(), login.password());
        var user = (User) authenticationManager.authenticate(a).getPrincipal();
        return ResponseEntity.ok(new TokenDto(jwtService.generateToken(user)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid Register user) {
        var novoUser = userService.createUser(user.name(), user.user(), user.password());
        return ResponseEntity.ok(novoUser);
    }
}