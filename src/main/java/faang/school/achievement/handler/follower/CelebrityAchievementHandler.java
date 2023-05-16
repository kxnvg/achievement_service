package faang.school.achievement.handler.follower;

import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementHolder;
import faang.school.achievement.service.AchievementService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CelebrityAchievementHandler extends FollowerEventHandler {

    public CelebrityAchievementHandler(AchievementService achievementService, AchievementHolder achievementHolder) {
        super(achievementService, achievementHolder);
    }

    @Override
    @Async
    @Transactional
    @Retryable
    public void handle(FollowerEvent event) {
        Achievement achievement = achievementHolder.getAchievement("Celebrity");
        if (!achievementService.hasAchievement(event.getFolloweeId(), achievement.getId())) {
            achievementService.createProgressIfNecessary(event.getFolloweeId(), achievement.getId());
            AchievementProgress progress = achievementService.getProgress(event.getFolloweeId(), achievement.getId());
            progress.increment();
            if (progress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(event.getFolloweeId(), achievement);
            }
        }
    }
}
