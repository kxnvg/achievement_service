package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkillEventListener extends AbstractListener<SkillAcquiredEventDto> {

    @Value("${spring.achievements.skill.title}")
    private String achievementTitle;

    public SkillEventListener(ObjectMapper objectMapper, List<EventHandler<SkillAcquiredEventDto>> handlers) {
        super(objectMapper, handlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        SkillAcquiredEventDto event = readValue(message.getBody(), SkillAcquiredEventDto.class);
        handleEvent(event, achievementTitle);
    }
}