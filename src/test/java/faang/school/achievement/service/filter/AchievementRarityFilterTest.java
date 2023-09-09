package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AchievementRarityFilterTest {

    private AchievementRarityFilter achievementRarityFilter;

    @BeforeEach
    void init() {
        achievementRarityFilter = new AchievementRarityFilter();
    }

    @Test
    void testIsApplicableAchievementRarityShouldReturnTrue() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .rarities(List.of(Rarity.RARE))
                .build();
        assertTrue(achievementRarityFilter.isApplicable(filterDto));
    }

    @Test
    void testIsApplicableAchievementRarityShouldReturnFalse() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .title("title")
                .build();
        assertFalse(achievementRarityFilter.isApplicable(filterDto));
    }

    @Test
    void testApply() {
        List<Achievement> achievements = List.of(
                Achievement.builder()
                        .id(1L)
                        .rarity(Rarity.COMMON)
                        .build(),
                Achievement.builder()
                        .id(2L)
                        .rarity(Rarity.EPIC)
                        .build(),
                Achievement.builder()
                        .id(3L)
                        .rarity(Rarity.COMMON)
                        .build(),
                Achievement.builder()
                        .id(4L)
                        .rarity(Rarity.RARE)
                        .build());
        AchievementFilterDto filter = AchievementFilterDto.builder()
                .rarities(List.of(Rarity.COMMON, Rarity.RARE))
                .build();
        var result = achievementRarityFilter.apply(achievements.stream(), filter).toList();

        assertEquals(List.of(
                Achievement.builder()
                        .id(1L)
                        .rarity(Rarity.COMMON)
                        .build(),
                Achievement.builder()
                        .id(3L)
                        .rarity(Rarity.COMMON)
                        .build(),
                Achievement.builder()
                        .id(4L)
                        .rarity(Rarity.RARE)
                        .build()), result);
    }
}
