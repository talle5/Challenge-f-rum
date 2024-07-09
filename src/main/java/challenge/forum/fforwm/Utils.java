package challenge.forum.fforwm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class Utils {
    @Bean
    public UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.newInstance();
    }

    @Bean @Primary
    public ObjectMapper objectMapper() {
        return (new ObjectMapper()).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
