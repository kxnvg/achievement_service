package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicEventDto {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String profilePicLink;
}
