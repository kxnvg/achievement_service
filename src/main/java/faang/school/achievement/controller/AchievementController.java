package faang.school.achievement.controller;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoAchievementProgress;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import faang.school.achievement.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @PostMapping("/all")
    public List<DtoAchievement> allAchievements(@Valid @RequestBody() DtoFilterAchievement dtoFilterAchievement) {
        return achievementService.allAchievements(dtoFilterAchievement);
    }

    @GetMapping("/user")
    public List<DtoAchievement> userAchievement() {
        return achievementService.userAchievement();
    }

    @GetMapping("/{achievementId}")
    public DtoAchievement getAchievement(@PathVariable long achievementId) {
        return achievementService.getAchievement(achievementId);
    }

    @GetMapping("/unearned")
    public List<DtoAchievementProgress> unearnedAchievement() {
        return achievementService.unearnedAchievements();
    }
}
