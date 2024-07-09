package challenge.forum.fforwm.domain.topic.dto;

import jakarta.validation.constraints.NotBlank;

public record EditTopic(@NotBlank String title, @NotBlank String message) {}
