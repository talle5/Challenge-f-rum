package challenge.forum.fforwm.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record Register(
        @NotBlank String name,
        @NotBlank String user,
        @NotBlank String password
) {}
