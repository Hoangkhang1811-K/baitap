package com.codegym.lesson456.controller;

import com.codegym.lesson456.model.Song;
import com.codegym.lesson456.model.SongForm;
import com.codegym.lesson456.service.SongService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/songs")
public class SongController {
    private static final Set<String> AUDIO_EXTENSIONS = Set.of("mp3", "wav", "ogg", "m4a", "aac");
    private final SongService service;
    @Value("${upload.directory}") private String uploadDirectory;
    public SongController(SongService service) { this.service = service; }

    @GetMapping public String list(Model model) {
        model.addAttribute("songs", service.findAll());
        return "songs/list";
    }
    @GetMapping("/create") public String create(Model model) {
        model.addAttribute("songForm", new SongForm());
        return "songs/form";
    }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) {
        Song song = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Song not found: " + id));
        SongForm form = new SongForm(); form.setId(song.getId()); form.setTitle(song.getTitle());
        form.setArtist(song.getArtist()); form.setGenre(song.getGenre());
        model.addAttribute("songForm", form);
        return "songs/form";
    }
    @PostMapping("/save") public String save(@ModelAttribute SongForm form, RedirectAttributes redirect) throws Exception {
        Song song = form.getId() == null ? new Song() : service.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("Song not found: " + form.getId()));
        song.setTitle(form.getTitle().trim()); song.setArtist(form.getArtist().trim()); song.setGenre(form.getGenre().trim());
        MultipartFile audio = form.getAudioFile();
        if (audio != null && !audio.isEmpty()) {
            String original = StringUtils.cleanPath(audio.getOriginalFilename() == null ? "track" : audio.getOriginalFilename());
            String extension = StringUtils.getFilenameExtension(original);
            String ext = extension == null ? "" : extension.toLowerCase(Locale.ROOT);
            if (!AUDIO_EXTENSIONS.contains(ext)) throw new IllegalArgumentException("File nhạc phải có định dạng mp3, wav, ogg, m4a hoặc aac");
            Path directory = Path.of(uploadDirectory, "music").toAbsolutePath().normalize();
            Files.createDirectories(directory);
            String filename = UUID.randomUUID() + "." + ext;
            audio.transferTo(directory.resolve(filename));
            song.setFilePath(filename);
        } else if (song.getFilePath() == null || song.getFilePath().isBlank()) {
            throw new IllegalArgumentException("Vui lòng chọn file nhạc");
        }
        service.save(song);
        redirect.addFlashAttribute("message", "Đã lưu bài hát");
        return "redirect:/songs";
    }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id, RedirectAttributes redirect) throws Exception {
        Song song = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Song not found: " + id));
        service.delete(song);
        Files.deleteIfExists(Path.of(uploadDirectory, "music", song.getFilePath()).toAbsolutePath().normalize());
        redirect.addFlashAttribute("message", "Đã xoá bài hát");
        return "redirect:/songs";
    }
}
