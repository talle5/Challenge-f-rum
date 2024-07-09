package challenge.forum.fforwm.domain.message;

import challenge.forum.fforwm.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity @Getter @Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Message pai;

    @ManyToOne
    User autor;

    String conteudo;

    @Temporal(TemporalType.TIMESTAMP)
    Instant criacao;

    protected Message() {}

    public Message(Message pai, User autor, String conteudo) {
        this.pai = pai;
        this.autor = autor;
        this.conteudo = conteudo;
        this.criacao = Instant.now();
    }

    public Message(User autor, String conteudo){
        this(null,autor,conteudo);
    }
}
