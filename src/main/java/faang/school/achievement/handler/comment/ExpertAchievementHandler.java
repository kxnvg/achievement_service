package faang.school.achievement.handler.comment;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends AbstractCommentEventHandler<CommentEventDto> {
    @Value("${spring.achievements.comment.expert.title}")
    private String achievementTitle;

    public ExpertAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(CommentEventDto event) {
        handleAchievement(achievementTitle, event.getAuthorId());
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}
