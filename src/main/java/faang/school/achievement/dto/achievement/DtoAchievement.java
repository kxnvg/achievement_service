package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class DtoAchievement {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
}
