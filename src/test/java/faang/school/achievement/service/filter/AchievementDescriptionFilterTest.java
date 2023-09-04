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
public class AchievementDescriptionFilterTest {
    private AchievementDescriptionFilter achievementDescriptionFilter;

    @BeforeEach
    void init() {
        achievementDescriptionFilter = new AchievementDescriptionFilter();
    }

    @Test
    void testIsApplicableAchievementDescriptionShouldReturnTrue() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .description("description")
                .build();
        assertTrue(achievementDescriptionFilter.isApplicable(filterDto));
    }

    @Test
    void testIsApplicableAchievementDescriptionShouldReturnFalse() {
        AchievementFilterDto filterDto = AchievementFilterDto.builder()
                .title("title")
                .build();
        assertFalse(achievementDescriptionFilter.isApplicable(filterDto));
    }

    @Test
    void testApply() {
        List<Achievement> achievements = List.of(
                Achievement.builder()
                        .description("superior ability")
                        .build(),
                Achievement.builder()
                        .description("special effort")
                        .build(),
                Achievement.builder()
                        .description("great courage")
                        .build());
        AchievementFilterDto filter = AchievementFilterDto.builder()
                .description("special effort")
                .build();
        var result = achievementDescriptionFilter.apply(achievements.stream(), filter).toList();

        assertEquals(List.of(
                Achievement.builder()
                        .description("special effort")
                        .build()), result);
    }
}
