package faang.school.achievement.messaging.postevent;

import faang.school.achievement.dto.post.PostEvent;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.awaitility.Awaitility.await;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostEventListenerTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withInitScript("db/changelog/changeset/testcontainers/users_V001_initial_for_testcontainers.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private AchievementProgressRepository achievementProgressRepository;

    @Test
    @Disabled
    void test() {
        Producer<String, PostEvent> producer = configureProducer();

        producer.send(new ProducerRecord<>("post-publication", mockPostEvent()));
        producer.close();

        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(2, TimeUnit.MINUTES)
                .untilAsserted(() -> {
                    Optional<AchievementProgress> test = achievementProgressRepository
                            .findByUserIdAndAchievementId(1L, 1L);

                    assertNotNull(test);
                });
    }

    private Producer<String, PostEvent> configureProducer() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new KafkaProducer(producerProps, new StringSerializer(), new JsonSerializer<PostEvent>());
    }

    private PostEvent mockPostEvent() {
        return PostEvent.builder()
                .postId(1L)
                .userAuthorId(1L)
                .projectAuthorId(null)
                .content("test")
                .publishedAt(LocalDateTime.now())
                .build();
    }
}