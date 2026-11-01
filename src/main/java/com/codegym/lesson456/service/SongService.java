package com.codegym.lesson456.service;

import com.codegym.lesson456.model.Song;
import com.codegym.lesson456.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongService {
    private final SongRepository repository;
    public SongService(SongRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true) public List<Song> findAll() { return repository.findAll(); }
    @Transactional(readOnly = true) public Optional<Song> findById(Long id) { return repository.findById(id); }
    public Song save(Song song) { return repository.save(song); }
    public void delete(Song song) { repository.delete(song); }
}
