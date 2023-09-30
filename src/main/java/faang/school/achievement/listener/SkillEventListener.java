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

    public SkillEventListener(
            ObjectMapper objectMapper,
            List<EventHandler<SkillAcquiredEventDto>> handlers,
            @Value("${spring.achievements.skill.master.title}") String achievementTitle) {
        super(objectMapper, handlers, achievementTitle);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        SkillAcquiredEventDto event = readValue(message.getBody(), SkillAcquiredEventDto.class);
        handleEvent(event);
    }
}