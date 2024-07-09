package challenge.forum.fforwm.domain.topic;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
public class Topic {

    public enum Estado {
        ATIVO, ENCERRRADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    @JsonManagedReference
    private User autor;

    @OneToOne(cascade = CascadeType.ALL)
    private Message message;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;

    @Enumerated
    private Estado estado;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> respostas;

    public Topic(String titulo, User autor, String message) {
        this.titulo = titulo;
        this.autor = autor;
        this.message = new Message(autor, message);
        this.dataCriacao = new Date();
        this.estado = Estado.ATIVO;
        this.respostas = new LinkedList<>();
    }

    protected Topic() {}

    public void close() {
        this.estado = Estado.ENCERRRADO;
    }

    public Boolean isClosed() {
        return this.estado == Estado.ENCERRRADO;
    }
}
