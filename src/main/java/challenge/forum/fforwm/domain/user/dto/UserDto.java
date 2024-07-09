package challenge.forum.fforwm.domain.user.dto;

import challenge.forum.fforwm.domain.user.User;

import java.util.Date;
import java.util.List;

public record UserDto(Long id, String name, List<Pair> topics, Date joined) {
    public UserDto(User user) {
        this(user.getId(),
             user.getName(),
             user.getTopics().stream().map(
                  t -> new Pair(t.getTitle(), t.getResponses().size(), t.getActive())
             ).toList(),
             user.getJoined());
    }

    private record Pair(String title, Integer responses, String estado) {
        public Pair(String title, Integer responses, Boolean estado) {
            this(title, responses, (estado) ? "Ativo" : "Encerrado");
        }
    }
}
