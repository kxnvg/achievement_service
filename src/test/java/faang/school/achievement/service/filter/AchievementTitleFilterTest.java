package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AchievementTitleFilterTest {
    private AchievementTitleFilter achievementTitleFilter;

    @BeforeEach
    void init() {
        achievementTitleFilter = new AchievementTitleFilter();
    }

    @Test
    void testIsApplicableAchievementDescriptionShouldReturnTrue() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .title("title")
                .build();
        assertTrue(achievementTitleFilter.isApplicable(filterDto));
    }

    @Test
    void testIsApplicableAchievementDescriptionShouldReturnFalse() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .description("description")
                .build();
        assertFalse(achievementTitleFilter.isApplicable(filterDto));
    }

    @Test
    void testApply() {
        List<Achievement> achievements = List.of(
                Achievement.builder()
                        .title("swordsman")
                        .build(),
                Achievement.builder()
                        .title("honor student")
                        .build(),
                Achievement.builder()
                        .title("mentor")
                        .build());
        AchievementFilterDto filter = AchievementFilterDto.builder()
                .title("swordsman")
                .build();
        var result = achievementTitleFilter.apply(achievements.stream(), filter).toList();

        assertEquals(List.of(
                Achievement.builder()
                        .title("swordsman")
                        .build()), result);
    }
}
