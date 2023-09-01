package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.handler.invite_event.InviteHandler;
import faang.school.achievement.handler.invite_event.OrganizerAchievementHandler;
import lombok.Data;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Data
public class InviteEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final OrganizerAchievementHandler organizerAchievementHandler;
    private final List<InviteHandler> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        InviteSentEventDto event;

        try {
            event = objectMapper.readValue(message.getBody(), InviteSentEventDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        handlers.stream().forEach(inviteHandler -> inviteHandler.handle(event));
    }
}
