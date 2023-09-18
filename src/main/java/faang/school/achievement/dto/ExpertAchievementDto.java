package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ExpertAchievementDto {
    private Long userId;
    private String achievementTitle;
    private ZonedDateTime createdAt;
}
