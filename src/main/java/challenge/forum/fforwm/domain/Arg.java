package challenge.forum.fforwm.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record Arg(
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        Date start,
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        Date end,
        String autor,
        String conteudo
) {}
