package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExpertAchievementDto {
    private Long userId;
    private String achievementTitle;
    private LocalDateTime createdAt;
}
