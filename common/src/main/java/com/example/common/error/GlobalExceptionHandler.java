package com.example.common.error;

import com.example.common.api.ApiResponse;
import com.example.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException, {}", ex.getMessage());
        var errorMessages = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String errMessages = err.getDefaultMessage();
            errorMessages.append(fieldName).append(": ");
            errorMessages.append(errMessages).append(" ");
        });

        final ApiResponse<Void> body = new ApiResponse<>(HttpStatus.BAD_REQUEST, errorMessages.toString());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(IllegalArgumentException ex) {
        log.error("handleIllegalArgumentException, {}", ex.getMessage());
        String message = ex.getMessage() == null ? HttpStatus.BAD_REQUEST.getReasonPhrase() : ex.getMessage();
        final ApiResponse<Void> body = new ApiResponse<>(HttpStatus.BAD_REQUEST, message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(BusinessException ex) {
        log.error("handleBusinessException, {}", ex.getLocalizedMessage());
        String message = ex.getLocalizedMessage() == null ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() : ex.getLocalizedMessage();
        final ApiResponse<Void> body = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("handleException, {}", ex.getLocalizedMessage());
        if(ex.getCause() instanceof IllegalArgumentException illegalArgumentException) {
            return handleException(illegalArgumentException);
        }
        String message = ex.getLocalizedMessage() == null ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() : ex.getLocalizedMessage();
        final ApiResponse<Void> body = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
