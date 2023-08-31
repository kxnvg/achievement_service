package faang.school.achievement.dto.follow.Mentorship;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class MentorshipEventDto {

    @NotNull
    private Long mentorId;

    @NotNull
    private Long userId;
}
