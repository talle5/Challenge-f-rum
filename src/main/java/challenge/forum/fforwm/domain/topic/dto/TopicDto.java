package challenge.forum.fforwm.domain.topic.dto;

import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.domain.topic.Topic;

import java.util.List;

public record TopicDto(
        Long id,
        String title,
        String author,
        String message,
        List<MessageDto> responses) {
    public TopicDto(Topic t) {
        this(t.getId(),
             t.getTitle(),
             t.getAuthor().getName(),
             t.getMessage().getContent(),
             t.getResponses().stream().map(MessageDto::new).toList());
    }
}
