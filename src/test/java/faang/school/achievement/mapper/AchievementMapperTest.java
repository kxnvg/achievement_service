package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementMapperTest {

    AchievementMapper achievementMapper = new AchievementMapperImpl();

    @Test
    void toDtoTest() {
        AchievementDto achievementDto = AchievementDto.builder()
                .id(1L)
                .title("Hello")
                .description("There")
                .build();
        Achievement expected = Achievement.builder()
                .id(1L)
                .title("Hello")
                .description("There")
                .build();

        Achievement result = achievementMapper.toEntity(achievementDto);

        assertEquals(expected, result);
    }
}