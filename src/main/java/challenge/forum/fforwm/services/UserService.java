package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.user.User;
import challenge.forum.fforwm.domain.user.dto.UserDto;
import challenge.forum.fforwm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public User createUser(String name, String userName, String password) {
        var user = new User(name, userName, encoder.encode(password));
        repository.save(user);
        return user;
    }

    public User me() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> allUsers() {
        return repository.findAll();
    }

    @Transactional
    public void mudarPassword(String password) {
        repository.getReferenceById(me().getId()).getCredenciais().setPassword(encoder.encode(password));
    }

    @Transactional
    public void mudarName(String name) {
        repository.getReferenceById(me().getId()).setName(name);
    }

    @Transactional
    public void mudarNick(String nick) {
        repository.getReferenceById(me().getId()).getCredenciais().setLogin(nick);
    }

    @Transactional
    public void delete(Long id) {
        if (isAuthorized(id)) {
            repository.deleteById(id);
        }
    }

    private Boolean isAuthorized(Long id) {
        return me() == repository.getReferenceById(id) || isAdmin();
    }

    private Boolean isAdmin() {
        return me().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }
}
