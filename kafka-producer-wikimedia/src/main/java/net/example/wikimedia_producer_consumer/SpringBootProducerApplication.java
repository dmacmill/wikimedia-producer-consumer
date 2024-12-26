package net.example.wikimedia_producer_consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Base springboot application class for the producer. Run this class to start the producer.
 * 
 * This is both the entry point for the application and the class that runs the producer thread.
 * It does this with the overridden run method from the CommandLineRunner interface.
 */
@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProducerApplication.class, args);
    }

    @Autowired
    private WikimediaChangesProducer wikimediaChangesProducer;

    @Override
    public void run(String... args) throws Exception {
        // run the producer thread
        wikimediaChangesProducer.sendMessage();
    }
}
