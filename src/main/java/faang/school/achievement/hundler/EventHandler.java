package faang.school.achievement.hundler;

public interface EventHandler<T> {

    void handle(Long userId);
}