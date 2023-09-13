package faang.school.achievement.message.handler;

import faang.school.achievement.dto.ExpertAchievementDto;
import faang.school.achievement.message.publisher.CommentEventPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Async
public class ExpertAchievementHandler extends CommentEventHandler<ExpertAchievementDto>{

    public ExpertAchievementHandler(AchievementService achievementService, CommentEventPublisher commentEventPublisher,
                                    @Value("${achievement.title.expert}") String achievementTitle) {
        super(achievementService, commentEventPublisher);
        goal = achievementService.getGoalForAchievement(achievementTitle);
        this.achievementTitle = achievementTitle;
    }

    protected void publishAchievement(Long userId){
        ExpertAchievementDto expertAchievementDto = new ExpertAchievementDto(userId, achievementTitle, LocalDateTime.now());
        commentEventPublisher.publish(expertAchievementDto);
    }
}
