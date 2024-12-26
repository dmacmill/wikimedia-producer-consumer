package net.example.wikimedia_producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import net.example.wikimedia_producer_consumer.entity.WikimediaData;
import net.example.wikimedia_producer_consumer.repository.WikimediaDataRepository;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
        topics = "wikimedia_recentchange", 
        groupId = "myGroup"
    )
    public void consume(String message) {
        logger.info(String.format("event consumed message: %s", message));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(message);
        dataRepository.save(wikimediaData);
    }
}
