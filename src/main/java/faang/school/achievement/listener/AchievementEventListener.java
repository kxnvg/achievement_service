package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AchievementEventListener extends AbstractListener<AchievementEventDto> {

    public AchievementEventListener(
            ObjectMapper objectMapper,
            List<EventHandler<AchievementEventDto>> handlers,
            @Value("${spring.achievements.achievement.recursion.title}") String achievementTitle
    ) {
        super(objectMapper, handlers, achievementTitle);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        AchievementEventDto event = readValue(message.getBody(), AchievementEventDto.class);
        handleEvent(event);
    }
}