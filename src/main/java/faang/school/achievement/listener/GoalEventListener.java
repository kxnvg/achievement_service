package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEventDto> implements MessageListener {
    private final List<EventHandler> eventHandlers;

    @Autowired
    public GoalEventListener(ObjectMapper objectMapper, List<EventHandler> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        GoalSetEventDto goalSetEventDto = convertJsonToString(message, GoalSetEventDto.class);
        eventHandlers.forEach(eventHandler -> eventHandler.handle(goalSetEventDto));
    }
}
