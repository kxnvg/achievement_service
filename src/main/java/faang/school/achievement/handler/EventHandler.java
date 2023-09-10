package faang.school.achievement.handler;

public interface EventHandler<T> {
    boolean handle(T event);
}
