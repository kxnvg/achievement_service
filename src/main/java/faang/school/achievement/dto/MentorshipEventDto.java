package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class MentorshipEventDto {
    private Long requesterId;
    private Long receiverId;
    private LocalDateTime createdAt;
}
