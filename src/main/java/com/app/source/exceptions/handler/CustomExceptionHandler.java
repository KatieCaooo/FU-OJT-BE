package com.app.source.exceptions.handler;

import com.app.source.exceptions.CompanyNotExistedException;
import com.app.source.exceptions.CrudException;
import com.app.source.exceptions.EmailAlreadyExistedException;
import com.app.source.exceptions.EmptyRoleException;
import com.app.source.exceptions.MajorNotExistedException;
import com.app.source.exceptions.UsernameAlreadyExistedException;
import com.app.source.payload.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyExistedException.class, UsernameAlreadyExistedException.class, EmptyRoleException.class,
            CompanyNotExistedException.class, MajorNotExistedException.class})
    protected ResponseEntity<Response> handleAuthExceptions(CrudException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(new Response("Error", exception.getMessage()));
    }

}
