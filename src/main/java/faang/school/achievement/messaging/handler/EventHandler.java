package faang.school.achievement.messaging.handler;

import org.springframework.stereotype.Component;

@Component
public interface EventHandler<T> {

    void handle(T event);
}
