package faang.school.achievement.handler.profile;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.ProfilePicEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;

public class HandsomeAchievementHandler extends AbstractProfileEventHandler<ProfilePicEventDto>{
    @Value("${spring.achievements.profile.handsome.title}")
    private String achievementTitle;

    public HandsomeAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(ProfilePicEventDto event) { handleAchievement(achievementTitle, event.getUserId());}

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}
