package com.blogging.exceptions;

import com.blogging.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> toHandleGlobalException(ResourceNotFoundException exception){
        ApiResponse response = new ApiResponse(exception.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> toHandleAccessDeniedException(AccessDeniedException exception){
        ApiResponse response = new ApiResponse(exception.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> toHandleValidationException(MethodArgumentNotValidException exception){

        Map<String,String> response = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    FieldError field = ((FieldError) objectError);
                    String fieldName = field.getField();
                    String message = field.getDefaultMessage();
                    response.put(fieldName,message);
                });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
