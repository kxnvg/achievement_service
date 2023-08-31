package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
//
//class AchievementMapperTest {

//    private AchievementMapper achievementMapper = new AchievementMapperImpl();
//
//    @Test
//    void toDtoTest() {
//        Achievement achievement = Achievement.builder()
//                .id(1)
//                .title("first title")
//                .description("first description")
//                .rarity(Rarity.COMMON)
//                .points(35)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now().minusMonths(3))
//                .build();
//
//        AchievementDto expected = AchievementDto.builder()
//                .id(1)
//                .title("first title")
//                .description("first description")
//                .rarity(Rarity.COMMON)
//                .points(35)
//                .build();
//
//        AchievementDto result = achievementMapper.toAchievementDto(achievement);
//
//        assertEquals(expected, result);
//    }
//}