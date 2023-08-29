package faang.school.achievement.dto.achievement;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoFilterAchievement {
    @Size(max = 128)
    private String title;
    @Size(max = 1024)
    private String description;
    @Pattern(regexp = "COMMON|UNCOMMON|RARE|EPIC|LEGENDARY", message = "the field can be RARE, COMMON, UNCOMMON, EPIC, LEGENDARY or null")
    private String rarity;
}
