package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.exception.DeserializeJSONException;
import faang.school.achievement.hundler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InviteEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<EventHandler> eventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        InviteSentEventDto inviteSentEventDto;
        try {
            inviteSentEventDto = objectMapper.readValue(message.getBody(), InviteSentEventDto.class);
        } catch (IOException e) {
            throw new DeserializeJSONException("Unable to deserialize invite sent event");
        }
        eventHandlers.forEach(handler -> handler.handle(inviteSentEventDto));
    }
}
