package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping("/{id}")
    public AchievementDto getAchievementInformation(long id){
        return achievementService.getAchievementInformation(id);
    }

    @GetMapping("/{userId}")
    public List<UserAchievementDto> getUserAchievements(@PathVariable long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/progress/{userId}")
    public List<AchievementProgressDto> getAllAchievementProgressByUserId(@PathVariable long userid) {
        return achievementService.getAllAchievementProgressByUserId(userid);
    }

    @GetMapping("/{achievementId}/progress/{userId}")
    public AchievementProgressDto getAchievementProgressByUserId(@PathVariable long achievementId, @PathVariable long userId) {
        return achievementService.getAchievementProgressByUserId(achievementId, userId);
    }

}
