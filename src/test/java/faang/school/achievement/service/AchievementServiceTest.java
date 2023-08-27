package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoAchievementProgress;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import faang.school.achievement.filters.Achievement.FilterAchievementDescription;
import faang.school.achievement.filters.Achievement.FilterAchievementName;
import faang.school.achievement.filters.Achievement.FilterAchievementRarity;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private UserContext userContext;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper = AchievementProgressMapper.INSTANCE;
    private final AchievementMapper achievementMapper = AchievementMapper.INSTANCE;
    @Mock
    private final List<DtoAchievement> dtoAchievements = new ArrayList<>();
    @InjectMocks
    private AchievementService achievementService;
    DtoAchievement achievement1 = new DtoAchievement();
    DtoAchievement achievement2 = new DtoAchievement();
    DtoAchievement achievement3 = new DtoAchievement();
    List<DtoAchievement> value;

    @BeforeEach
    void setup() {
        achievement1.setTitle("tomato");
        achievement1.setId(1L);
        achievement1.setDescription("tomatos");
        achievement1.setRarity(Rarity.EPIC);

        achievement2.setTitle("tiger");
        achievement2.setId(2L);
        achievement2.setDescription("tigers");
        achievement2.setRarity(Rarity.LEGENDARY);

        achievement3.setTitle("bear");
        achievement3.setId(3L);
        achievement3.setDescription("bears");
        achievement3.setRarity(Rarity.RARE);
        value = List.of(achievement1, achievement2, achievement3);
    }

    @Test
    void allAchievements() {
        DtoFilterAchievement filters = new DtoFilterAchievement();
        filters.setTitle("tomato");
        AchievementService service = new AchievementService(userContext, achievementRepository, userAchievementRepository, achievementProgressRepository,
                List.of(new FilterAchievementName(), new FilterAchievementDescription(), new FilterAchievementRarity()), value);

        List<DtoAchievement> expected = service.allAchievements(filters);
        List<DtoAchievement> actual = List.of(achievement1);

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
    }

    @Test
    void userAchievement() {
        when(userContext.getUserId()).thenReturn(1L);
        achievementService.userAchievement();
        verify(userAchievementRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getAchievement() {
        long id = 2L;
        when(achievementRepository.findById(1L)).thenReturn(Optional.of(new Achievement()));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> achievementService.getAchievement(id));
    }

    @Test
    void unearnedAchievements() {
        AchievementProgress progress1 = new AchievementProgress();
        when(userContext.getUserId()).thenReturn(1L);
        when(achievementProgressRepository.findByUserId(1L)).thenReturn(List.of(progress1));
        List<DtoAchievementProgress> expected = achievementService.unearnedAchievements();
        assertEquals(expected.get(0).getClass(), achievementProgressMapper.achievementProgressToDto(progress1).getClass());
    }
}