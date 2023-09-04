package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.DataValidationException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private AchievementMapper achievementMapper;

    @Mock
    private UserAchievementMapper userAchievementMapper;

    @Mock
    private AchievementProgressMapper achievementProgressMapper;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    void testGetAllAchievements() {

        Achievement achievement1 = Achievement.builder()
                .id(1L)
                .title("title1")
                .build();

        Achievement achievement2 = Achievement.builder()
                .id(2L)
                .title("title2")
                .build();

        List<Achievement> achievementList = List.of(achievement1, achievement2);

        AchievementDto dto1 = AchievementDto.builder()
                .id(1L)
                .title("title1")
                .build();
        AchievementDto dto2 = AchievementDto.builder()
                .id(2L)
                .title("title2")
                .build();

        List<AchievementDto> achievementDtoList = List.of(dto1, dto2);

        when(achievementRepository.findAll())
                .thenReturn(achievementList);
        when(achievementMapper.toDto(achievement1)).thenReturn(dto1);
        when(achievementMapper.toDto(achievement2)).thenReturn(dto2);
        List<AchievementDto> result = achievementService.getAllAchievements();
        assertEquals(achievementDtoList, result);
    }

    @Test
    void testGetAchievementById() {
        Long id = 1L;
        Achievement achievement = Achievement.builder()
                .id(1L)
                .title("title1")
                .build();
        AchievementDto dto1 = AchievementDto.builder()
                .id(2L)
                .title("title2")
                .build();
        when(achievementRepository.findById(id)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(dto1);
        var result = achievementService.getAchievementById(id);
        assertEquals(dto1, result);
    }

    @Test
    void testGetAchievementByIdShouldReturnDataValidationException() {
        Long id = 1L;
        assertThrows(DataValidationException.class, () -> achievementService.getAchievementById(id));
    }

    @Test
    void testGetUserAchievements() {
        Long userId = 1L;
        UserAchievement userAchievement1 = UserAchievement.builder().id(2L).build();
        UserAchievement userAchievement2 = UserAchievement.builder().id(3L).build();
        UserAchievementDto dto1 = UserAchievementDto.builder().id(2L).build();
        UserAchievementDto dto2 = UserAchievementDto.builder().id(3L).build();
        List<UserAchievement> userAchievementList = List.of(userAchievement1, userAchievement2);
        List<UserAchievementDto> userAchievementDtoList = List.of(dto1, dto2);

        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievementList);
        when(userAchievementMapper.toDto(userAchievement1)).thenReturn(dto1);
        when(userAchievementMapper.toDto(userAchievement2)).thenReturn(dto2);
        var result = achievementService.getUserAchievements(userId);
        assertEquals(userAchievementDtoList, result);
    }

    @Test
    void testGetUserAchievementProgresses() {
        Long userId = 1L;
        AchievementProgress achievementProgress = AchievementProgress.builder()
                .id(3L)
                .userId(1L)
                .build();
        AchievementProgressDto dto = AchievementProgressDto.builder().id(3L).userId(1L).build();
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(achievementProgress));
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(dto);
        var result = achievementService.getUserAchievementProgresses(userId);
        assertEquals(List.of(dto), result);
    }

}
