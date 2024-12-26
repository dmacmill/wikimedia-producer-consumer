package net.example.wikimedia_producer_consumer.entity;

// note: jakarta.persistence.Entity instead of javax.persistence.Entity
import jakarta.persistence.Entity; // This annotator just ensures this class is a JPA entity with unique name.
import jakarta.persistence.GeneratedValue; // This annotator is used to specify the primary key generation strategy.
import jakarta.persistence.GenerationType; // This annotator contains various strategies for primary key generation.
import jakarta.persistence.Id; // the identifier of the table row
import jakarta.persistence.Lob; // Large objects: the wikimedia entry is pretty large
import jakarta.persistence.Table; // Indicates this is data for a table in a db.

import lombok.Getter; // These annotators add getters and setters automatically
import lombok.Setter;

@Entity
@Table(name = "wikimedia_recentchange")
@Getter
@Setter
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    private String wikiEventData;
}
