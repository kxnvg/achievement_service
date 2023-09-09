package faang.school.achievement.message.publisher;

public interface MessagePublisher<T> {
    void publish(T message);
}
