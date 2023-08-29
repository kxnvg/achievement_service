package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class DtoAchievement {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;

    public void setRarity(int rarity) {
        if (rarity == 1) {
            this.rarity = Rarity.COMMON;
        }
        if (rarity == 2) {
            this.rarity = Rarity.UNCOMMON;
        }
        if (rarity == 3) {
            this.rarity = Rarity.RARE;
        }
        if (rarity == 4) {
            this.rarity = Rarity.EPIC;
        }
        if (rarity == 5) {
            this.rarity = Rarity.LEGENDARY;
        }
    }
}
