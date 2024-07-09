package challenge.forum.fforwm.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String login, @NotBlank String password) {}