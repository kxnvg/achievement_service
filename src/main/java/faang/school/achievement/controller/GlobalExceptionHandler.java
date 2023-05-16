package faang.school.achievement.controller;

import faang.school.achievement.dto.ErrorResponse;
import faang.school.achievement.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(fromBusinessException(e));
    }

    private ErrorResponse fromBusinessException(BusinessException e) {
        return new ErrorResponse(
                e.getCode(),
                e.getMessage()
        );
    }
}
