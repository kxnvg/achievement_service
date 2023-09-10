package faang.school.achievement.messaging.builder;

import org.springframework.cglib.core.Local;

public interface MessageBuilder<T> {
    boolean supportsEventType(T event);
    String buildMessage(T event, Local local);
}
