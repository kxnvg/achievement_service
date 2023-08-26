package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.listener.event.InviteSentEvent;
import lombok.Data;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class InviteEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private List<InviteSentEvent> eventList = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String eventString = String.valueOf(message.getBody());
        InviteSentEvent event;
        try {
            event = objectMapper.readValue(eventString, InviteSentEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        eventList.add(event);
    }
}
