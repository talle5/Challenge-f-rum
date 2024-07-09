package challenge.forum.fforwm.repository;

import challenge.forum.fforwm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String c);
}
