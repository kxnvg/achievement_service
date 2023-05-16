package faang.school.achievement.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserAchievementDto {
    private long userId;
    private List<AchievementDto> achievements;
}
