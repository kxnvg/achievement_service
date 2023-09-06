package faang.school.achievement.handler;

import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CollectorAchievementHandler implements EventHandler<GoalSetEventDto> {
    private final AchievementService achievementService;
    private final String title;
    private final String description;
    private final Integer points;
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    public CollectorAchievementHandler(AchievementService achievementService,
                                       @Value("${achievements.goal.collector.title}") String title,
                                       @Value("${achievements.goal.collector.description}") String description,
                                       @Value("${achievements.goal.collector.points}") Integer points){
        this.achievementService = achievementService;
        this.title = title;
        this.description = description;
        this.points = points;
    }

    @Async
    @Override
    public void handle(GoalSetEventDto event) {

        Achievement achievement = getByTitle(title);

        if (!achievementService.hasAchievement(event.getUserId(), achievement.getId())) {
            AchievementProgress achievementProgress = getAchievementProgress(event, achievement);

            achievementProgress.setUpdatedAt(LocalDateTime.now());
            achievementProgress.increment();

            achievementProgress = achievementService.updateAchievementProgress(achievementProgress);

            if (achievementProgress.getCurrentPoints() == achievement.getPoints()) {
                lock.lock();
                try {
                    UserAchievement userAchievement = UserAchievement.builder()
                            .achievement(achievement)
                            .userId(event.getUserId())
                            .build();
                    if (!achievementService.hasAchievement(achievement.getId(), event.getUserId())) {
                        achievementService.giveAchievement(userAchievement);
                    }
                } finally {
                    lock.unlock();
                }
            }

        }

    }

    private Achievement getByTitle(String title) {
        Optional<Achievement> achievement = achievementService.getAchievementByTitle(title);
        return achievement.orElseGet(this::createAchievement);
    }

    private AchievementProgress getAchievementProgress(GoalSetEventDto goalSetEventDto, Achievement achievement) {
        Optional<AchievementProgress> progress =
                achievementService.getAchievementProgress(goalSetEventDto.getUserId(), achievement.getId());

        if (progress.isEmpty()) {
            AchievementProgress achievementProgress = AchievementProgress.builder()
                    .achievement(achievement)
                    .userId(goalSetEventDto.getUserId())
                    .currentPoints(0)
                    .version(1)
                    .build();

            return achievementService.createAchievementProgressIfNecessary(achievementProgress);
        } else {
            return progress.get();
        }
    }

    private Achievement createAchievement() {
        Achievement achievement = Achievement.builder()
                .title(title)
                .description(description)
                .rarity(Rarity.COMMON)
                .points(points)
                .build();

        return achievementService.createAchievement(achievement);
    }
}
