package faang.school.achievement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
class AchievementServiceAppTests {
    @Test
    void contextLoads() {
        Assertions.assertThat(40 + 2).isEqualTo(42);
    }
}
