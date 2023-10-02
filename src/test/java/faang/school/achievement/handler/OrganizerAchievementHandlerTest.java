package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrganizerAchievementHandlerTest {

    @Mock
    private ThreadPoolTaskExecutor executor;

    @InjectMocks
    private OrganizerAchievementHandler handler;

    private InviteSentEventDto eventDto;

    @BeforeEach
    void setUp() {
        eventDto = InviteSentEventDto.builder()
                .authorId(1l)
                .build();
    }

    @Test
    void testHandle() {
        handler.handle(eventDto);
        verify(executor).execute(any(Runnable.class));
    }
}