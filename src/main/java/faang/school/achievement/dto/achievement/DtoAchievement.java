package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAchievement {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
}
