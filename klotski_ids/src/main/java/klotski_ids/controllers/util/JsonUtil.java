package klotski_ids.controllers.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(File file, Class<T> valueType) throws IOException {
        return objectMapper.readValue(file, valueType);
    }
}
