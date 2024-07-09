package challenge.forum.fforwm;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<?> errob() {
        return ResponseEntity.badRequest().body("{\"erro\":\"json invalido\"}");
    }

    //    @ExceptionHandler(Exception.class)
    //    public ResponseEntity<?> qwe(Exception e) {
    //        System.out.printf("%s:\t%s\n", e.getClass(), e.getMessage());
    //        return ResponseEntity.badRequest().build();
    //    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> erro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> a(Exception e) {
        return ResponseEntity.badRequest().body(String.format("{\"ERROR\":\"%s\"}", e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> erro() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> duplicateUserName(Exception e) {
        return ResponseEntity.badRequest().body(e.getCause());
    }


    //    @ExceptionHandler(MethodArgumentNotValidException.class)
    //    public ResponseEntity tratarErro400(MethodArgumentNotValidException e) {
    //        var erros = e.getFieldErrors();
    //        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    //    }
}
