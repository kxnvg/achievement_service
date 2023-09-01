package faang.school.achievement.handler.invite_event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.EventDto;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@Data
@EnableAsync
@Slf4j
public class OrganizerAchievementHandler implements InviteHandler {

    private final AchievementService service;
    private final AchievementCache cache;
    private String OrganizerAchievementName = "organizer";

    @Override
    @Async("eventHandleExecutor")
    public void handle(EventDto eventDto) {
        Achievement achievement = cache.get(OrganizerAchievementName).orElseThrow(() -> new EntityNotFoundException());

        long userId = getUserId(eventDto);
        long achievementId = achievement.getId();

        if (!service.hasAchievement(userId, achievementId)) {
            service.createProgressIfNecessary(userId, achievementId);
            long progress = service.getProgress(userId, achievementId);
            progress++;
            if (progress >= achievement.getPoints()) {
                service.giveAchievement(userId, achievementId);
                log.info("Achievement {} has been given to user with id {}.", OrganizerAchievementName, userId);
            }
        }
    }

    private long getUserId(EventDto eventDto) {
        InviteSentEventDto event;
        if (eventDto instanceof InviteSentEventDto) {
            event = (InviteSentEventDto) eventDto;
            log.info("InviteSentEventDto with authorId {} has been handled.", event.getAuthorId());
        } else {
            log.error("EventDto is not InviteSentEventDto.");
            throw new RuntimeException();
        }
        return event.getAuthorId();
    }
}
