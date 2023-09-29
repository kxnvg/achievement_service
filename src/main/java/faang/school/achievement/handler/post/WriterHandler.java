package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterHandler extends AbstractPostEventHandler<PostEventDto> {
    @Value("${spring.achievements.post.writer.title}")
    private String achievementTitle;

    public WriterHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(PostEventDto event) {
        handleAchievement(achievementTitle, event.getAuthorId());
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}