package faang.school.achievement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JsonMapper {
    private ObjectMapper objectMapper;

    public <T> T toObject(String json, Class<T> valueType) throws InstantiationException, IllegalAccessException {
        T object = valueType.newInstance();
        try {
            object = objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            log.error("Exception with json mapping: " + e.getMessage());
        }
        return object;
    }
}
