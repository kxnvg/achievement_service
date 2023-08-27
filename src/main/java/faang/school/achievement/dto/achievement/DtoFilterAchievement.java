package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoFilterAchievement {
    @Max(128)
    private String title;
    @Max(1024)
    private String description;
    @Pattern(regexp = "COMMON|UNCOMMON|RARE|EPIC|LEGENDARY", message = "the field can be RARE, COMMON, UNCOMMON, EPIC, LEGENDARY or null")
    private Rarity rarity;
}
