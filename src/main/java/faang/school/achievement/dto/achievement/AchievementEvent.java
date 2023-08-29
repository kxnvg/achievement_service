package faang.school.achievement.dto.achievement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementEvent {
    private long userId;
    private String title;
    private String description;
}
