package faang.school.achievement.achievementHandler.invitation;

import faang.school.achievement.dto.invitation.StageInvitationEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.invitation.OrganizerAchievementHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizerAchievementHandlerTest {
    @Mock
    AchievementService service;
    @Mock
    AchievementInMemCache cache;
    @InjectMocks
    OrganizerAchievementHandler handler;
    @Value("${achievement.organizer}")
    private String achievementTitle;

    @ParameterizedTest
    @MethodSource("getPairIds")
    @DisplayName("Process achievement")
    void process(long authorId, long stageId) {
        Achievement achievement = new Achievement();
        StageInvitationEvent event = new StageInvitationEvent();
        event.setAuthorId(authorId);
        event.setInvitedId(stageId);
        achievement.setTitle(achievementTitle);

        when(cache.getAchievement(achievementTitle))
                .thenReturn(Optional.of(achievement));

        handler.handle(event);

        verify(service, times(1)).updateAchievementProgress(event.getAuthorId(), achievement);
    }

    @Test
    @DisplayName("Process achievement. Should throw exception")
    void processShouldThrowException() {
        when(cache.getAchievement(achievementTitle))
                .thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> handler.handle(new StageInvitationEvent()));
        assertEquals("Achievement not found", exception.getMessage());
    }

    private static Stream<Arguments> getPairIds() {
        return Stream.of(
                Arguments.of(
                        nextLong(), nextLong()
                ),
                Arguments.of(
                        nextLong(), nextLong()
                ),
                Arguments.of(
                        nextLong(), nextLong()
                ),
                Arguments.of(
                        nextLong(), nextLong()
                ),
                Arguments.of(
                        nextLong(), nextLong()
                )
        );
    }
}