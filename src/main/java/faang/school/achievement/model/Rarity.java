package faang.school.achievement.model;

import lombok.Getter;

@Getter
public enum Rarity {
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    EPIC(4),
    LEGENDARY(5);
    private final int number;

    Rarity(int number) {
        this.number = number;
    }
}
