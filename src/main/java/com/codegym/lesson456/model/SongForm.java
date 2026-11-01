package com.codegym.lesson456.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private Long id;
    private String title;
    private String artist;
    private String genre;
    private MultipartFile audioFile;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public MultipartFile getAudioFile() { return audioFile; }
    public void setAudioFile(MultipartFile audioFile) { this.audioFile = audioFile; }
}
