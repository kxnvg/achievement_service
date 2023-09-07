package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class OpinionLeaderAchievementHandler extends AbstractAchievementHandler<EventPostDto> {

    private final String ACHIEVEMENT_TITTLE = "Opinion leader";

    public OpinionLeaderAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Async("threadPoolForAchievementHandler")
    @Override
    public void handle(EventPostDto postDto) {
        long userId = getIfOfPostAuthor(postDto);
        handleAchievement(userId, ACHIEVEMENT_TITTLE);
    }

    private long getIfOfPostAuthor(EventPostDto postDto) {
        long postAuthorId;
        if (postDto.getAuthorId() == null) {
            postAuthorId = postDto.getProjectId();
        } else {
            postAuthorId = postDto.getAuthorId();
        }
        return postAuthorId;
    }
}