package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.topic.Topic;
import challenge.forum.fforwm.exception.IllegalAction;
import challenge.forum.fforwm.exception.UnauthorizedAction;
import challenge.forum.fforwm.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static challenge.forum.fforwm.services.UserService.isAuthorized;
import static challenge.forum.fforwm.services.UserService.me;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final MessageService messageService;

    public Page<Topic> listAll(Pageable pagina, Boolean all) {
        return all ? topicRepository.findAll(pagina) : topicRepository.findAllByActive(true, pagina);
    }

    public Topic getById(Long id) {
        return topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Topic> search(Pageable pagina, String assunto) {
        return topicRepository.findAllByAssunto(assunto, pagina);
    }

    @Transactional
    public Topic createTopic(String title, String message) {
        return topicRepository.save(new Topic(title, me(), message));
    }

    @Transactional
    public void closeTopic(Long id) {
        var topic = topicRepository.getReferenceById(id);
        if (isAuthorized(topic.getAuthor().getId())) {
            topic.close();
        } else throw new UnauthorizedAction();

    }

    @Transactional
    public Message reply(Long topicId, String response) {
        var topic = topicRepository.getReferenceById(topicId);
        if (!topic.isClosed()) {
            var message = messageService.replyMessage(topic.getMessage().getId(), response);
            topic.getResponses().add(message);
            return message;
        } else throw new IllegalAction("topic encerrado");
    }

    @Transactional
    public Message replyMessage(Long topicId, Long messageId, String response) {
        var topic = topicRepository.getReferenceById(topicId);
        if (!topic.isClosed()) {
            var message = messageService.replyMessage(messageId, response);
            topic.getResponses().add(message);
            return message;
        } else throw new IllegalAction("topic encerrado");
    }

    @Transactional
    public void editTopic(Long id, String title, String content) {
        var topic = topicRepository.getReferenceById(id);
        if (isAuthorized(topic.getAuthor().getId())) {
            topic.setTitle(title);
            topic.getMessage().setContent(content);
        } else throw new UnauthorizedAction();
    }

    @Transactional
    public void deleteTopic(Long id) {
        var topic = topicRepository.getReferenceById(id);
        if (isAuthorized(topic.getAuthor().getId())) {
            topicRepository.deleteById(id);
        } else throw new UnauthorizedAction();
    }
}
