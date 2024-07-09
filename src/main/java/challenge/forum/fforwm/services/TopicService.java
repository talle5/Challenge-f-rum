package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.topic.Topic;
import challenge.forum.fforwm.domain.topic.dto.TopicDto;
import challenge.forum.fforwm.domain.user.User;
import challenge.forum.fforwm.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository repo;

    public Page<TopicDto> allTopic(Pageable pagina, Boolean all) {
        return (all ? repo.findAll(pagina) : repo.findAllByEstado(Topic.Estado.ATIVO, pagina)).map(TopicDto::new);
    }

    public Topic topicById(Long id) {
        return repo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Topic> allTopic(Pageable pagina, String assunto) {
        return repo.findAllByAssunto(assunto, pagina);
    }

    public Page<Topic> searchBetweenData(Date data1, Date data2, Pageable pagina) {
        return repo.findAllByDataCriacaoBetween(data1,data2, pagina);
    }

    @Transactional
    public Topic createTopic(String titulo, String message) {
        var topic = new Topic(titulo, getAuthenticatedUser(), message);
        repo.save(topic);
        return topic;
    }

    @Transactional
    public void closeTopic(Long id) {
        var topic = repo.getReferenceById(id);
        if (getAuthenticatedUser() == topic.getAutor()) {
            topic.close();
        } else {
            throw new RuntimeException("Ação não autorizada");
        }
    }

    @Transactional
    public Message reply(Long topicId, String resposta) {
        var topic = repo.getReferenceById(topicId);
        if (!topic.isClosed()) {
            var message = new Message(topic.getMessage(), getAuthenticatedUser(), resposta);
            topic.getRespostas().add(message);
            return message;
        } else throw new RuntimeException("topic encerrado");
    }

    @Transactional
    public void editTopic(Long id, challenge.forum.fforwm.domain.topic.dto.EditTopic new_info) {
        var topic = repo.getReferenceById(id);
        if (topic.getAutor() == getAuthenticatedUser()) {
            topic.setTitulo(new_info.titulo());
            topic.getMessage().setConteudo(new_info.message());
        } else throw new RuntimeException("Ação não autorizada");
    }

    @Transactional
    public void deleteTopic(Long id) {
        var topic = repo.getReferenceById(id);
        if (topic.getAutor() == getAuthenticatedUser()) {
            repo.deleteById(id);
        } else throw new RuntimeException("Ação não autorizada");
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
