package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AchievementFilterDto {
    private String title;
    private String description;
    private List<Rarity> rarities;
}
