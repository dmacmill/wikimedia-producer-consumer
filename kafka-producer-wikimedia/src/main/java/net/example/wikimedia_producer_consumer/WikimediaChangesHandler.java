package net.example.wikimedia_producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;


/* This class triggers whenever there is a new MessageEvent.
 * In this case, from the wikimedia stream that will be sent through this. 
 * 
 * (BackgroundEventHandler from okhttp-eventsource is new replacing EventHandlers from the old version)
 * (it has onOpen, onClosed, onMessage, onComment, onError. We are only using onMessage)
 */
public class WikimediaChangesHandler implements BackgroundEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        // System.out.println("Connection opened");
    }

    @Override
    public void onClosed() {
        // System.out.println("Connection closed");
    }

    /* New message from a stream (like from http), send to Kafka
     * 
     * @param event: the event type
     * @param messageEvent: the message event, getData returns string data
     */
    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        LOGGER.info(String.format("Sending message='%s'", messageEvent.getData()));
        
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String comment) {
        // System.out.println("Comment: " + comment);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error: " + t);
    }

}
