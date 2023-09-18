package faang.school.achievement.controller;

import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/{userId}")
    public Long getNumberOfCommentsForUser(@PathVariable Long userId, @RequestParam Long achievementId){
        return achievementService.getNumberOfCommentsForUser(userId, achievementId);
    }
}
