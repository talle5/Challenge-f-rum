package challenge.forum.fforwm.domain.user;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.topic.Topic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity @Getter @Setter
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "autor",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    List<Topic> topic;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.REMOVE)
    @JsonBackReference
    List<Message> messages;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Credenciais credenciais;

    @Temporal(TemporalType.DATE)
    Date ingresso;

    protected User() {}

    public User(String name, String nickname, String password) {
        this.name = name;
        this.topic = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.credenciais = new Credenciais(nickname, password);
        this.ingresso = new Date();
    }

    public void deletUser() {
        this.name = "DELETED";
        this.credenciais = null;
        this.ingresso = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("HOLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.credenciais.password;
    }

    @Override
    public String getUsername() {
        return this.credenciais.login;
    }

    @Entity @Getter @Setter
    public static class Credenciais {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true)
        private String login;
        private String password;

        protected Credenciais() {}

        public Credenciais(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
}
