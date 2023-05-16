package faang.school.achievement.handler.follower;

import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.service.AchievementHolder;
import faang.school.achievement.service.AchievementService;

public abstract class FollowerEventHandler extends EventHandler<FollowerEvent> {

    public FollowerEventHandler(AchievementService achievementService, AchievementHolder achievementHolder) {
        super(achievementService, achievementHolder);
    }
}
