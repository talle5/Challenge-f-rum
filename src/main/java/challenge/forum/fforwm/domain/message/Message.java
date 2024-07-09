package challenge.forum.fforwm.domain.message;

import challenge.forum.fforwm.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Table(name = "mensagems")
@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Message pai;

    @ManyToOne
    private User author;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant created;

    protected Message() {}

    public Message(Message pai, User author, String content) {
        this.pai = pai;
        this.author = author;
        this.content = content;
        this.created = Instant.now();
    }

    public Message(User author, String content) {
        this(null, author, content);
    }
}
