package faang.school.achievement.message.handler;

import faang.school.achievement.dto.ExpertAchievementDto;
import faang.school.achievement.message.publisher.CommentEventPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public abstract class CommentEventHandler<T> {
    protected long goal;
    protected String achievementTitle;
    protected final AchievementService achievementService;
    protected final CommentEventPublisher commentEventPublisher;
    public void createProgressIfNecessary(Long userId){
        achievementService.createProgress(userId, getAchievement().getId());
    }

    public boolean isCompleted(Long userId){
        return achievementService.checkHasUserAchievement(userId, getAchievement().getId());
    }

    public void addPoint(Long userId){
        Long currentPoints = achievementService.addPoint(userId, getAchievement().getId());
        if(currentPoints >= goal){
            achievementService.addAchievementForUser(userId, getAchievement());
            publishAchievement(userId);
        }
    }

    protected Achievement getAchievement(){
        return achievementService.getAchievement(achievementTitle);
    }

    protected abstract void publishAchievement(Long userId);

}
