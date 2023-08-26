package faang.school.achievement.messaging.invitation;

import faang.school.achievement.achievementHandler.invitation.StageInvitationAchievement;
import faang.school.achievement.dto.invitation.StageInvitationEvent;
import faang.school.achievement.util.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InvitationListener implements MessageListener {
    private final JsonMapper mapper;
    private final List<StageInvitationAchievement> achievements;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        StageInvitationEvent event = readValue(message);
        achievementProcess(event);
    }

    private void achievementProcess(StageInvitationEvent event) {
        achievements.forEach(achievement -> achievement.process(event));
    }

    private StageInvitationEvent readValue(Message message) {
        return mapper
                .toObject(Arrays.toString(message.getBody()), StageInvitationEvent.class)
                .orElseThrow(() -> new RuntimeException("Could not deserialize message"));
    }
}
