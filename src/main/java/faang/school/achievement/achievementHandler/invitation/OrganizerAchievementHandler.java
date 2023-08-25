package faang.school.achievement.achievementHandler.invitation;

import faang.school.achievement.dto.invitation.StageInvitationEvent;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrganizerAchievementHandler extends StageInvitationAchievement {

    @Override
    @Async
    public void process(StageInvitationEvent event) {

    }
}

