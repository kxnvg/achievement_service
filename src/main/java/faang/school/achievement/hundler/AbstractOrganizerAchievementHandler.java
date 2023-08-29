package faang.school.achievement.hundler;

import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
public abstract class AbstractOrganizerAchievementHandler implements EventHandler {

    private AchievementService achievementService;
    private UserAchievementService userAchievementService;
    private AchievementProgressService achievementProgressService;
    private final ReentrantLock lock = new ReentrantLock();
    private String title;
    private String description;
    private long points;

    @Async
    @Override
    public void handle(InviteSentEventDto inviteSentEventDto) {
        Achievement achievement = getByTitle(title);

        boolean exists = userAchievementService.hasAchievement(achievement.getId(), inviteSentEventDto.getAuthorId());

        if (!exists) {
            AchievementProgress achievementProgress = getAchievementProgress(inviteSentEventDto, achievement);

            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(inviteSentEventDto, achievement, achievementProgress);
        }
    }

    private void giveAchievement(InviteSentEventDto inviteSentEvent, Achievement achievement, AchievementProgress progress) {
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

    private AchievementProgress getAchievementProgress(InviteSentEventDto inviteSentEventDto, Achievement achievement) {
        Optional<AchievementProgress> progress =
                achievementProgressService.getProgress(inviteSentEventDto.getAuthorId(), achievement.getId());

        if (progress.isEmpty()) {
            AchievementProgress achievementProgress = AchievementProgress.builder()
                    .achievement(achievement)
                    .userId(inviteSentEventDto.getAuthorId())
                    .currentPoints(0)
                    .version(1)
                    .build();

            return achievementProgressService.createProgressIfNecessary(achievementProgress);
        } else {
            return progress.get();
        }
    }

    private Achievement getByTitle(String title) {
        Optional<Achievement> achievement = achievementService.getByTitle(title);
        return achievement.orElseGet(this::createAchievement);
    }

    private Achievement createAchievement() {
        Achievement achievement = Achievement.builder()
                .title(title)
                .description(description)
                .rarity(Rarity.RARE)
                .points(points)
                .build();

        return achievementService.createAchievement(achievement);
    }
}
