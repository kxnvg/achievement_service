package faang.school.achievement.dto;

import lombok.Data;

@Data
public class TaskEventDto {
    private Long userId;
    private Long projectId;
    private Long taskId;
}
