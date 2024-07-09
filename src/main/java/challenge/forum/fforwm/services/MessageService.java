package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.exception.UnauthorizedAction;
import challenge.forum.fforwm.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static challenge.forum.fforwm.services.UserService.isAuthorized;
import static challenge.forum.fforwm.services.UserService.me;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Page<Message> searchMessage(String str, Pageable page) {
        return messageRepository.findAllByContentContains(str, page);
    }

    @Transactional
    public Message replyMessage(Long id, String message) {
        return messageRepository.save(new Message(messageRepository.getReferenceById(id), me(), message));
    }

    @Transactional
    public Message editMessage(Long id, String content) {
        var message = messageRepository.getReferenceById(id);
        message.setContent(content);
        return message;
    }

    @Transactional
    public void deleteMessage(Long id) {
        var message = messageRepository.getReferenceById(id);
        if (isAuthorized(message.getAuthor().getId())) {
            messageRepository.deleteById(id);
        } else throw new UnauthorizedAction();
    }
}
