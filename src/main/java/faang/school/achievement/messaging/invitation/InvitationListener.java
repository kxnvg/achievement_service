package faang.school.achievement.messaging.invitation;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.achievementHandler.invitation.InvitationAchievement;
import faang.school.achievement.dto.invitation.InvitationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InvitationListener implements MessageListener {
    private final ObjectMapper mapper;
    private final List<InvitationAchievement> achievements;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        InvitationEvent event = readValue(message);
        achievementProcess(event);
    }

    private void achievementProcess(InvitationEvent event) {
        achievements.forEach(achievement -> achievement.process(event));
    }

    private InvitationEvent readValue(Message message) {
        try {
            return mapper.readValue(message.getBody(), InvitationEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize message", e);
        }
    }
}
