package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementService;

public interface EventHandler<T> {
    boolean handle(T event);
}
