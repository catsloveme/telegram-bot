package ru.tinkoff.edu.java.scrapper.repository.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.java.scrapper.entity.Links;

public interface JpaLinkRepository extends JpaRepository<Links, Long> {
    Links findByUrl(String url);

    boolean existsByUrl(String url);

    @Query(value = "select l from Links l where l.lastCheckTime <= :checkPeriod")
    List<Links> getAllUncheckedLinks(OffsetDateTime checkPeriod);

    int removeByUrl(String url);

    void deleteAll();

}
