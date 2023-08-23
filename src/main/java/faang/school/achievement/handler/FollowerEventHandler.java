package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import org.springframework.stereotype.Component;

@Component
public abstract class FollowerEventHandler implements EventHandler<FollowerEventDto>{

    public abstract void handle(FollowerEventDto event);
}
