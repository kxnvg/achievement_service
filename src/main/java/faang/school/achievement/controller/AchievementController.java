package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementDto> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @PostMapping
    public List<AchievementDto> getAllAchievements(@RequestBody AchievementFilterDto filter) {
        return achievementService.getAllAchievements(filter);
    }

    @GetMapping("/{id}")
    public AchievementDto getAchievementById(@PathVariable Long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping("/byUser/{userId}")
    public List<UserAchievementDto> getUserAchievements(@PathVariable Long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/progresses/{userId}")
    public List<AchievementProgressDto> getUserAchievementProgresses(@PathVariable Long userId) {
        return achievementService.getUserAchievementProgresses(userId);
    }
}
