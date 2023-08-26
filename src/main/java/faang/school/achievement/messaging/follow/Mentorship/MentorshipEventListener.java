package faang.school.achievement.messaging.follow.Mentorship;

import faang.school.achievement.dto.follow.Mentorship.MentorshipEventDto;
import faang.school.achievement.messaging.follow.AbstractEventListener;
import faang.school.achievement.service.handler.SenseyAchievementHandler.SenseyAchievementHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MentorshipEventListener extends AbstractEventListener<MentorshipEventDto> implements MessageListener {
    private final SenseyAchievementHandler senseyAchievementHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipEventDto mentorshipEventDto = handleEvent(message, MentorshipEventDto.class);
        senseyAchievementHandler.handle(mentorshipEventDto);
    }
}
