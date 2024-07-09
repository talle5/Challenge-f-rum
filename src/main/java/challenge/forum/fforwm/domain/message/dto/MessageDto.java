package challenge.forum.fforwm.domain.message.dto;

import challenge.forum.fforwm.domain.message.Message;

public record MessageDto(Long id, String author, String content) {
    public MessageDto(Message m) {
        this(m.getId(), m.getAuthor().getName(), m.getContent());
    }
}