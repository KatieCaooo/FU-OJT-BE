package ojt.management.exceptions.handler;

import ojt.management.exceptions.*;
import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ojt.management.exceptions.EmailAlreadyExistedException.class, UsernameAlreadyExistedException.class, EmptyRoleException.class,
            CompanyNotExistedException.class, MajorNotExistedException.class, AccountIdDoseNotExistException.class})
    protected ResponseEntity<Response> handleAuthExceptions(CrudException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new Response("Error", exception.getMessage()));
    }

}
