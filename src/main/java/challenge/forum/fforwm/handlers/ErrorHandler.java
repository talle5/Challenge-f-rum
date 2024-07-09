package challenge.forum.fforwm.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<?> invalidJson() {
        return ResponseEntity.badRequest().body("{\"erro\":\"json invalido\"}");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> EntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> invalidCredentials(Exception e) {
        return ResponseEntity.badRequest().body(String.format("{\"erro\":\"%s\"}", e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> userNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> duplicateUserName(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldsErrorDto> invalidField(MethodArgumentNotValidException e) {
        var erros = new FieldsErrorDto(e.getFieldErrors());
        return ResponseEntity.badRequest().body(erros);
    }

    public record FieldsErrorDto(Map<String, String> erros) {
        public FieldsErrorDto(List<FieldError> fields) {
            this(fields.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        }
    }

}
