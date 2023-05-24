package faang.school.achievement.dto;

import lombok.Data;

@Data
public class AchievementProgressDto {
    private long id;
    private long userId;
    private AchievementDto achievement;
    private int currentPoints;
    private int points;
}
