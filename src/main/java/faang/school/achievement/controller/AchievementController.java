package faang.school.achievement.controller;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoAchievementProgress;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/all-achievements")
    public List<DtoAchievement> allAchievements(@RequestBody() DtoFilterAchievement dtoFilterAchievement) {
        return achievementService.allAchievements(dtoFilterAchievement);
    }

    @GetMapping("/user-achievement")
    public List<DtoAchievement> userAchievement() {
        return achievementService.userAchievement();
    }

    @GetMapping("/get-achievement/{achievementId}")
    public DtoAchievement getAchievement(@PathVariable long achievementId) {
        return achievementService.getAchievement(achievementId);
    }

    @GetMapping("/unearned-achievements")
    public List<DtoAchievementProgress> unearnedAchievement() {
        return achievementService.unearnedAchievements();
    }
}
