package faang.school.achievement.dto.event;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class AchievementEvent {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
    private long userId;
}
