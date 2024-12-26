package net.example.wikimedia_producer_consumer;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
// below is old okhttp-eventsource API
// import com.launchdarkly.eventsource.HttpConnectStrategy;
// import com.launchdarkly.eventsource.MessageEvent;
// import com.launchdarkly.eventsource.StreamEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;


/* Service to pass kafkaTemplate to WikimediaChangesHandler, and to start the connection to Wikimedia.
 * 
 * Call sendMessage to start the connection, and it will run for 10 minutes.
 */
@Service
public class WikimediaChangesProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

     /*  we now need to send messages from a stream.
      *  To do this, we need libraries... like okhttp-eventsource maven
      *  
      *  See pom.xml for the dependencies we use. search maven + the dependency for the link.
      *  OLD
    
      public void send(String message) {
        LOGGER.info("Sending message='{}'", message);
        kafkaTemplate.send("wikimedia_recentchange", message);
      }
      */

     /* Setups a connection to wikimedia, configures the connection, configures and starts the
      * WikimediaChangesHandler, and starts the connection in a separate thread.
      * 
      * Times out after 10 minutes.
      */
     public void sendMessage() throws InterruptedException{
        String topic = "wikimedia_recentchange";

        // read real-time stream data from wikimedia, we made elsewhere.
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        BackgroundEventHandler myHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        BackgroundEventSource bes = new BackgroundEventSource.Builder(myHandler, 
            new EventSource.Builder(
                ConnectStrategy.http(URI.create(url))
                .connectTimeout(5, TimeUnit.SECONDS)
            )
        )
        .threadPriority(Thread.NORM_PRIORITY)
        .build();
        bes.start();

        // stop everything after 10 minutes
        TimeUnit.MINUTES.sleep(10);
    }
}
