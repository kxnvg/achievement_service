package faang.school.achievement.eventhandler.postevent;

import faang.school.achievement.dto.post.PostEvent;
import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WriterAchievementHandler implements EventHandler<PostEvent> {

    private final AchievementService achievementService;

    @Value("${achievements.writer_achievement.name}")
    private String ACHIEVEMENT_NAME;

    @SneakyThrows
    @Override
    @Async(value = "writerAchievementExecutor")
    public void handle(PostEvent event) {
        Achievement achievement = achievementService.getAchievementByTitle(ACHIEVEMENT_NAME);

        achievementService.updateAchievementProgress(event.getUserAuthorId(), achievement);

        log.info(event + " has been handled successfully");
    }
}
