package com.devpedrod.apiuserregister.controllers.exceptions;

import com.devpedrod.apiuserregister.dto.response.Response;
import com.devpedrod.apiuserregister.dto.response.ValidationError;
import com.devpedrod.apiuserregister.exceptions.DataIntegrityException;
import com.devpedrod.apiuserregister.exceptions.MethodArgumentNotValidException;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Response> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        Response response = Response.builder()
                .timeStamp(now())
                .status(NOT_FOUND)
                .statusCode(NOT_FOUND.value())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<Response> dataIntegrityException(DataIntegrityException e, HttpServletRequest request) {
        Response response = Response.builder()
                .timeStamp(now())
                .status(CONFLICT)
                .statusCode(CONFLICT.value())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError validationError = ValidationError.builder()
                .timeStamp(now())
                .status(UNPROCESSABLE_ENTITY)
                .statusCode(UNPROCESSABLE_ENTITY.value())
                .message("Erro de validação")
                .path(request.getRequestURI())
                .build();
        e.getMessages().forEach(x -> validationError.addError(x.getFieldName(), x.getMessage()));
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(validationError);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Response> springValidationError(org.springframework.web.bind.MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError validationError = ValidationError.builder()
                .timeStamp(now())
                .status(UNPROCESSABLE_ENTITY)
                .statusCode(UNPROCESSABLE_ENTITY.value())
                .message("Erro de validação")
                .path(request.getRequestURI())
                .build();
        e.getFieldErrors().forEach(x -> validationError.addError(x.getField(), x.getDefaultMessage()));
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(validationError);
    }
}
