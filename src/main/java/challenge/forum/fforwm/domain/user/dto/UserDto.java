package challenge.forum.fforwm.domain.user.dto;

import challenge.forum.fforwm.domain.topic.Topic;
import challenge.forum.fforwm.domain.user.User;

import java.util.Date;
import java.util.List;

public record UserDto(
        Long id,
        String name,
        List<String> topic,
        Date ingresso) {
    public UserDto(User user) {
        this(user.getId(),
             user.getName(),
             user.getTopic().stream().map(Topic::getTitulo).toList(),
             user.getIngresso());
    }
}
