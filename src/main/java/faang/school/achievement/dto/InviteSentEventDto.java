package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InviteSentEventDto implements EventDto {
    private Long authorId;
    private Long invitedId;
    private Long projectId;
}
