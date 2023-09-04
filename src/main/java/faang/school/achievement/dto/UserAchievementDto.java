package faang.school.achievement.dto;

import faang.school.achievement.model.Achievement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAchievementDto {
    private long id;
    private Long achievementId;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
