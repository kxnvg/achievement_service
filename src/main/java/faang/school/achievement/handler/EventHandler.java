package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementHolder;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EventHandler<T> {

    protected final AchievementService achievementService;
    protected final AchievementHolder achievementHolder;

    public abstract void handle(T event);
}
