package faang.school.achievement.achievementHandler;

import faang.school.achievement.dto.invitation.StageInvitationEvent;

public interface EventHandler {

    void process(StageInvitationEvent event);
}
