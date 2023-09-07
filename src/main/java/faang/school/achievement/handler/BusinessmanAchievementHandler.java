package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.service.ProjectAchievementService;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractProjectAchievementHandler<ProjectEventDto> {
    public BusinessmanAchievementHandler(ProjectAchievementService projectAchievementService) {
        super(projectAchievementService);
    }

    @Override
    public void handle(ProjectEventDto projectEventDto) {
        Achievement achievement = achievementService.getAchievement(title);

        boolean exists = userAchievementService.hasAchievement(achievement.getId(), inviteSentEventDto.getAuthorId());

        if (!exists) {
            AchievementProgress achievementProgress = getAchievementProgress(inviteSentEventDto, achievement);
            achievementProgress.setUpdatedAt(null);
            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(inviteSentEventDto, achievement, achievementProgress);
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

    @Override
    public boolean examinationEvent(ProjectEventDto event) {
        return event.getClass() == ProjectEventDto.class;
    }

    public void hasAchievement() {

    }

    public void createProgressIfNecessary() {

    }

    public void getProgress() {

    }

    public void giveAchievement() {

    }
}
