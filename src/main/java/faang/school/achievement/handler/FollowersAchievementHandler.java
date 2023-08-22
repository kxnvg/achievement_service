package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class FollowersAchievementHandler implements EventHandler<FollowerEventDto> {

    private final AchievementRepository achievementRepository;

    @Async("followerHandlerThreadPoolExecutor")
    @Override
    public void handle(FollowerEventDto event) {

    }
}