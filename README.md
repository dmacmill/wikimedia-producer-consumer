# Wikimedia Producer-Consumer

Producer-Consumer 2 microservice project that has one module produce stream data, and another module consume stream data.

Producer is a maven module called "kafka-producer-wikimedia"
 - The Producer uses okhttp-eventsource (google okhttp-eventsource maven) to subscribe to all wikimedia changes on 
   https://stream.wikimedia.org/v2/stream/recentchange on spun up thread.
 - The Producer Contains an okhttp-eventsource BackgroundEventHandler called "WikimediaChangesHandler" that passes the 
   streamed message on to Kafka upon receiving it.

Consumer is another maven module called "kafka-consumer-database"
 - The Consumer uses KafkaListener in KafkaDatabaseConsumer to consume data from our producer microservice.
 - There exists, btw, a data model WikimediaData using JPA and Hibernate for ORM and application.properties has the 
   connection details.
 - A JPARepository named WikimediaRepository with WikimediaData gets the consumed data saved to it. The mysql database 
   gets populated.

Considering:
 - Either consumer turns string -> JSON and store only attributes we find interesting
 - Another maven module that does this or something similar.

## quickstart
Maven:
 - bottom of explorer open Maven tab and right click `wikimedia-producer-consumer`, use Maven commands "clean" and "install"
 - ensure apache kafka is running, both a zookeeper and a broker (see step 2 of https://kafka.apache.org/quickstart)
 - run both SpringBootConsumerApplication.java and then SpringBootProducerApplication.java, enjoy your data.

## How To Reproduce
Watch https://www.youtube.com/watch?v=inrQUHLPFd4 and go to https://start.spring.io/ for generating this springboot project.

- Use "Spring for Apache Kafka" as well as "Lombok" (reduces boilerplate).
- In pom.xml, add `<packaging>pom</packaging>` to the project.
- Right click on project explorer, click Maven > New Module ...
- Wikimedia-producer-consumer for the parent, and name kafka-producer-wikimedia
- This new kafka-producer-wikimedia will not be Spring Boot, so add new project "net.___.wikimedia_producer_consumer"
- Add new class in there: "SpringBootProducerApplication" and write spring boot boilerplate. Ensure everything runs.
- in bottom of explorer, open Maven tab and reload with that cycle icon button.
- right click on kafka-producer-wikimedia in Maven and run maven commands "clean" and "install"
- We have to make kafka-producer-wikimedia as a java file, so add `<packaging>jar</packaging>`
  to kafka-producer-wikimedia/pom.xml file.
- Repeat with kafka-consumer-database, watch the video above for different packages, though. 
