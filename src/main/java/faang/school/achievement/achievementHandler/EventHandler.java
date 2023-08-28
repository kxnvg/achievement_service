package faang.school.achievement.achievementHandler;

public interface EventHandler<T> {

    void process(T event);
}
