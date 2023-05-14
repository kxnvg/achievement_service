package faang.school.achievementservice;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients("faang.school.achievementservice.client")
public class AchievementServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AchievementServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
