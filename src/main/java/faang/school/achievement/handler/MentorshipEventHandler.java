package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class MentorshipEventHandler implements EventHandler<MentorshipEventDto> {

    @Override
    public abstract void handler(MentorshipEventDto eventDto);
}
