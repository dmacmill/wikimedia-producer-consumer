package net.example.wikimedia_producer_consumer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/* Configuration class for kafka topics.
 * 
 * Contains a spring bean that holds topic metadata for kafka.
 */
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("wikimedia_recentchange")
        .build();
    }
}
