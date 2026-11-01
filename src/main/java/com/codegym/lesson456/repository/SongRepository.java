package com.codegym.lesson456.repository;

import com.codegym.lesson456.model.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SongRepository {
    @PersistenceContext private EntityManager entityManager;
    public List<Song> findAll() {
        return entityManager.createQuery("select s from Song s order by s.id desc", Song.class).getResultList();
    }
    public Optional<Song> findById(Long id) { return Optional.ofNullable(entityManager.find(Song.class, id)); }
    public Song save(Song song) { return entityManager.merge(song); }
    public void delete(Song song) { entityManager.remove(entityManager.contains(song) ? song : entityManager.merge(song)); }
}
