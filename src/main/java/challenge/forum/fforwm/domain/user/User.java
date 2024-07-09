package challenge.forum.fforwm.domain.user;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "usuarios")
@Entity
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Topic> topics;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @Column(unique = true)
    private String login;
    
    private String password;

    @Temporal(TemporalType.DATE)
    private Date joined;

    protected User() {}

    public User(String name, String nickname, String password) {
        this.name = name;
        this.topics = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.login = nickname;
        this.password = password;
        this.joined = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("HOLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }
}