package challenge.forum.fforwm.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupDto(
        @NotBlank String name,
        @NotBlank String user,
        @NotBlank String password
) {}
