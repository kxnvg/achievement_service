package faang.school.achievement.listener;

import faang.school.achievement.dto.MentorshipEventDto;
import faang.school.achievement.handler.MentorshipEventHandler;
import faang.school.achievement.mapper.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MentorshipMessageListener implements MessageListener {

    private final List<MentorshipEventHandler> handlers;
    private final JsonObjectMapper jsonObjectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipEventDto eventDto = jsonObjectMapper.readValue(message.getBody(), MentorshipEventDto.class);
        handlers.forEach(handler -> handler.handler(eventDto));
    }
}
