package com.Projects.marketplace.exception;

import com.Projects.marketplace.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String,String>errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error->{errors.put(error.getField(),error.getDefaultMessage());
                });
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("validation failed")
                .time(LocalDateTime.now())
                .errors(errors)
                .url(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .url(request.getRequestURI())
                .time(LocalDateTime.now())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistException(UserAlreadyExistException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .url(request.getRequestURI())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmailDomainNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailDomainNotFoundException(EmailDomainNotFoundException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameNotFoundException(UsernameNotFoundException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .url(request.getRequestURI())
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCrdentials(BadCredentialsException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCategoryNotFoundException(ProductNotFoundException ex, HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCategoryNotFoundException(CategoryNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnAuthorizedException(UnAuthorizedException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRunTimeException(RuntimeException ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .time(LocalDateTime.now())
                .url(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex,HttpServletRequest request){
        ErrorResponseDto response=ErrorResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .url(request.getRequestURI())
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
