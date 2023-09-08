package faang.school.achievement.service;

import faang.school.achievement.publisher.AchievementEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementEventPublisher achievementEventPublisher;


}
