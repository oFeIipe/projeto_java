package projeto.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserAlreadyExists.class)
        private ResponseEntity<String> userAlreadyExistsHandler(UserAlreadyExists ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existente");
    }

}
