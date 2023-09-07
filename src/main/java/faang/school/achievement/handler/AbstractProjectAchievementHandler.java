package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.ProjectAchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.AllArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
public abstract class AbstractProjectAchievementHandler<T> implements EventHandler<T> {
    private final ProjectAchievementService projectAchievementService;
    private final AchievementService achievementService;
    private final UserAchievementService userAchievementService;
    private final ReentrantLock lock = new ReentrantLock();


    public void handle(ProjectEventDto projectEventDto) {

        Achievement achievement = getByTitle(title);

        if (!achievementService.hasAchievement(projectEventDto.getUserId(), achievement.getId())) {
            AchievementProgress achievementProgress = getAchievementProgress(projectEventDto, achievement);

            achievementProgress.setUpdatedAt(LocalDateTime.now());
            achievementProgress.increment();

            achievementProgress = achievementService.updateAchievementProgress(achievementProgress);

            if (achievementProgress.getCurrentPoints() == achievement.getPoints()) {
                lock.lock();
                try {
                    UserAchievement userAchievement = UserAchievement.builder()
                            .achievement(achievement)
                            .userId(projectEventDto.getUserId())
                            .build();
                    if (!achievementService.hasAchievement(achievement.getId(), projectEventDto.getUserId())) {
                        achievementService.giveAchievement(userAchievement);
                    }
                } finally {
                    lock.unlock();
                }
            }

        }

    }

    private void giveAchievement(ProjectEventDto projectEventDto, Achievement achievement, AchievementProgress progress) {
        if (progress.getCurrentPoints() == achievement.getPoints()) {
            lock.lock();
            try {
                UserAchievement userAchievement = UserAchievement.builder()
                        .achievement(achievement)
                        .userId(inviteSentEvent.getAuthorId())
                        .build();

                if (!userAchievementService.hasAchievement(achievement.getId(), inviteSentEvent.getAuthorId())) {
                    userAchievementService.giveAchievement(userAchievement);
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
