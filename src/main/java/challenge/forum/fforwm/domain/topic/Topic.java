package challenge.forum.fforwm.domain.topic;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private User author;

    @OneToOne(cascade = CascadeType.ALL)
    private Message message;

    @Temporal(TemporalType.DATE)
    private Date created;

    private Boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Message> responses;

    public Topic(String title, User author, String message) {
        this.title = title;
        this.author = author;
        this.message = new Message(author, message);
        this.created = new Date();
        this.active = true;
        this.responses = new LinkedList<>();
    }

    protected Topic() {}

    public void close() {
        this.active = false;
    }

    public Boolean isClosed() {
        return !this.active;
    }
}
