package faang.school.achievement.achievementHandler.invitation;

import faang.school.achievement.dto.invitation.InvitationEvent;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrganizerAchievementHandler implements InvitationAchievement {

    @Override
    @Async
    public void process(InvitationEvent event) {
    }
}

