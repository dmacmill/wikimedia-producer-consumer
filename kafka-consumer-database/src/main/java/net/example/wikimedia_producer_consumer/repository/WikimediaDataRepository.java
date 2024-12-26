package net.example.wikimedia_producer_consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.wikimedia_producer_consumer.entity.WikimediaData;

/* JPA repository that takes our WikimediaData entity (Model) and Long (Primary Key). 
 * 
 * Default methods include findAll(), findById(), save(), delete(), etc.
 * These are fine for us and don't need editing for now.
 */
public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}
