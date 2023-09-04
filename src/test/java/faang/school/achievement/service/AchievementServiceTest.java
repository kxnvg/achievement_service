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
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @InjectMocks
    private AchievementService achievementService;
    DtoAchievement achievement1;
    DtoAchievement achievement2;
    DtoAchievement achievement3;
    List<DtoAchievement> value;

    @Test
    void allAchievements() {
        createAchievementDto();
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
        createAchievementDto();
        UserAchievement userAchievement1 = UserAchievement.builder().achievement(achievementMapper.dtoToAchievement(achievement1)).build();
        UserAchievement userAchievement2 = UserAchievement.builder().achievement(achievementMapper.dtoToAchievement(achievement2)).build();
        when(userContext.getUserId()).thenReturn(1L);
        when(userAchievementRepository.findByUserId(1L)).thenReturn(List.of(userAchievement1, userAchievement2));
        List<DtoAchievement> expected = achievementService.userAchievement();
        verify(userAchievementRepository, times(1)).findByUserId(1L);
        assertEquals(expected.get(0).getTitle(), achievement1.getTitle());
        assertEquals(expected.get(1).getTitle(), achievement2.getTitle());
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

    private void createAchievementDto() {
        achievement1 = new DtoAchievement();
        achievement2 = new DtoAchievement();
        achievement3 = new DtoAchievement();
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
    public void testHasAchievement() {
        long userId = 1L;
        long achievementId = 2L;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(true);

        boolean hasAchievement = achievementService.hasAchievement(userId, achievementId);

        assertTrue(hasAchievement);
    }

    @Test
    public void testGiveAchievement() {
        long userId = 1L;
        Achievement achievement = new Achievement(); // Создайте нужный Achievement

        achievementService.giveAchievement(userId, achievement);

        verify(userAchievementRepository).save(any(UserAchievement.class));
    }

    @Test
    public void testGetAchievementProgressExisting() {
        long userId = 1L;
        long achievementId = 2L;
        AchievementProgress progress = new AchievementProgress(); // Создайте нужный AchievementProgress

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(progress));

        AchievementProgress result = achievementService.getAchievementProgress(userId, achievementId);

        assertNotNull(result);
        assertEquals(progress, result);
    }

    @Test
    public void testGetAchievementProgressNonExisting() {
        long userId = 1L;
        long achievementId = 2L;

        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());

        AchievementProgress result = achievementService.getAchievementProgress(userId, achievementId);

        assertNull(result);
    }
}