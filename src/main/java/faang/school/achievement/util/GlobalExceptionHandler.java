package faang.school.achievement.util;

import faang.school.achievement.util.exception.AchievementNotCreatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AchievementNotCreatedException.class)
    public ResponseEntity<Object> handleInvalidFieldException(AchievementNotCreatedException ex) {
        log.error("Exception caused: {}. \n" +
                "Stacktrace: {}", ex.getMessage(), ex.getStackTrace());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
