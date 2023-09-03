package faang.school.achievement.messaging.postevent;

import faang.school.achievement.dto.post.PostEvent;
import faang.school.achievement.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener {

    private final List<EventHandler<PostEvent>> postEventHandlers;

    @KafkaListener(topics = "post-publication")
    public void listen(PostEvent event) {
        log.info("Received post-view event: {}", event);

        postEventHandlers.forEach(handler -> handler.handle(event));
    }
}
