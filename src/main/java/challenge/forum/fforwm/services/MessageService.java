package challenge.forum.fforwm.services;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.domain.user.User;
import challenge.forum.fforwm.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository repo;

    public Page<MessageDto> searchMessage(String str, Pageable page) {
        return repo.findAllByConteudoContains(str, page).map(MessageDto::new);
    }

    @Transactional
    public Message replyMessage(Long id, MessageDto message) {
        var newmessage = new Message(repo.getReferenceById(id), getAuthenticatedUser(), message.message());
        repo.save(newmessage);
        return newmessage;
    }

    @Transactional
    public Message editMessage(Long id, MessageDto men) {
        var message = repo.getReferenceById(id);
        message.setConteudo(men.message());
        return message;
    }

    @Transactional
    public void deleteMessage(Long id) {
        repo.deleteById(id);
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
