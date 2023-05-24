package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping("/achievement")
    public List<AchievementDto> getAchievements(@RequestBody AchievementFilterDto filter) {
        return achievementService.getAchievements(filter);
    }

    @GetMapping("/achievement/{id}")
    public AchievementDto getAchievement(@PathVariable long id) {
        return achievementService.getAchievement(id);
    }

    @GetMapping("/user/{userId}/achievement")
    private List<AchievementDto> getUserAchievements(@PathVariable long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/user/{userId}/achievement/progress")
    private List<AchievementProgressDto> getUserProgress(@PathVariable long userId) {
        return achievementService.getUserProgress(userId);
    }
}
