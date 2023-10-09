package faang.school.achievement.handler;

import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrganizerAchievementHandler extends AbstractAchievementHandler<InviteSentEventDto> {

    private final ThreadPoolTaskExecutor executor;
    @Value("${achievement-service.achievement.organizer.name}")
    private String organizerAchievementName;

    @Autowired
    public OrganizerAchievementHandler(AchievementService achievementService, @Qualifier("inviteSentEventThreadPoolExecutor") ThreadPoolTaskExecutor executor) {
        super(achievementService);
        this.executor = executor;
    }

    @Override
    public void handle(InviteSentEventDto event) {
        log.info("OrganizerAchievementHandler handle was called.");
        executor.execute(() -> {
            long userId = event.getAuthorId();
            handleAchievement(userId, organizerAchievementName);
        });
    }
}
