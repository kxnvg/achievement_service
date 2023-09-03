package faang.school.achievement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="achievement")
public class Achievement implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true, length = 128)
    private String title;

    @Column(name = "description", nullable = false, unique = true, length = 1024)
    private String description;

    @Column(name = "rarity", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Rarity rarity;

    @OneToMany(mappedBy = "achievement", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UserAchievement> userAchievements;

    @OneToMany(mappedBy = "achievement", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AchievementProgress> progresses;

    @Column(name = "points", nullable = false)
    private long points;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rarity=" + rarity +
                ", userAchievements=" + userAchievements +
                ", progresses=" + progresses +
                ", points=" + points +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
