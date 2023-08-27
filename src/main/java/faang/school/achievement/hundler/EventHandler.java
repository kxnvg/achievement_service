package faang.school.achievement.hundler;

import faang.school.achievement.dto.InviteSentEventDto;

public interface EventHandler {

    void handle(InviteSentEventDto inviteSentEventDto);
}
