package challenge.forum.fforwm.domain.topic.dto;

import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.domain.topic.Topic;

import java.util.List;

public record TopicDto(
        Long id,
        String titulo,
        String autor,
        String message,
        List<MessageDto> respostas) {
    public TopicDto(Topic t) {
        this(t.getId(),
             t.getTitulo(),
             t.getAutor().getName(),
             t.getMessage().getConteudo(),
             t.getRespostas().stream().map(MessageDto::new).toList());
    }
}
