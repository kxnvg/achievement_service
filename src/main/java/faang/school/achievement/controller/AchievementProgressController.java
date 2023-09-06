package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievementProgress")
@Slf4j
public class AchievementProgressController {

    private final AchievementProgressService achievementProgressService;

    @GetMapping("/achievement/{achievementId}/user/{userId}")
    public AchievementProgressDto getAchievementProgressByUserId(@PathVariable long achievementId, @PathVariable long userId) {
        log.info("Received GET request for achievementId: {} and userId: {}", achievementId, userId);
        return achievementProgressService.getAchievementProgressByUserId(achievementId, userId);
    }

    @GetMapping("/user/{userId}")
    public List<AchievementProgressDto> getAchievementsProgressByUserId(@PathVariable long userId) {
        log.info("Received GET request for userId: {}", userId);
        return achievementProgressService.getAchievementsProgressByUserId(userId);
    }
}
