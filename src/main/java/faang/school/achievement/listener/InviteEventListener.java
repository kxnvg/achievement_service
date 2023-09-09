package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.hundler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InviteEventListener extends AbstractEventListener<InviteSentEventDto> {
    private final List<EventHandler> eventHandlers;

    @Autowired
    public InviteEventListener(ObjectMapper objectMapper, List<EventHandler> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        InviteSentEventDto inviteSentEventDto = convertJsonToString(message, InviteSentEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(inviteSentEventDto.getAuthorId()));
    }
}
