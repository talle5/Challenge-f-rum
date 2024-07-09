package challenge.forum.fforwm.domain.message.dto;

import challenge.forum.fforwm.domain.message.Message;

public record MessageDto(Long id, String autor, String message) {
    public MessageDto(Message m) {
        this(m.getId(), m.getAutor().getName(), m.getConteudo());
    }
}