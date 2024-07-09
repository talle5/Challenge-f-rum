package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.user.User;
import challenge.forum.fforwm.exception.UnauthorizedAction;
import challenge.forum.fforwm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(String name, String userName, String password) {
        return userRepository.save(new User(name, userName, passwordEncoder.encode(password)));
    }

    public static User me() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void changePassword(String password) {
        userRepository.getReferenceById(me().getId()).setPassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void changeName(String name) {
        userRepository.getReferenceById(me().getId()).setName(name);
    }

    @Transactional
    public void changeNick(String nick) {
        userRepository.getReferenceById(me().getId()).setLogin(nick);
    }

    @Transactional
    public void delete(Long id) {
        if (isAuthorized(id)) {
            userRepository.deleteById(id);
        } else throw new UnauthorizedAction();
    }

    public static Boolean isAuthorized(Long id) {
        return Objects.equals(me().getId(), id) || isAdmin();
    }

    private static Boolean isAdmin() {
        return me().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }
}
