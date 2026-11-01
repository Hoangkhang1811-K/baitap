package com.codegym.lesson456.controller;

import com.codegym.lesson456.model.ApodImage;
import com.codegym.lesson456.service.ApodFeedbackService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Controller
public class ApodController {
    private static final String APOD_ENDPOINT = "https://science.nasa.gov/wp-json/wp/v2/apod-basic";
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ApodFeedbackService feedbackService;

    public ApodController(ApodFeedbackService feedbackService) { this.feedbackService = feedbackService; }

    @GetMapping("/apod")
    public String apod(Model model) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(APOD_ENDPOINT))
                    .timeout(Duration.ofSeconds(20)).header("Accept", "application/json")
                    .header("User-Agent", "Lesson456-SpringMVC/1.0").GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300)
                throw new IllegalStateException("NASA trả về HTTP " + response.statusCode());

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode item = root.isArray() ? root.path(0) : root;
            if (item.isMissingNode() || item.isNull()) throw new IllegalStateException("NASA chưa có dữ liệu ảnh hôm nay.");

            String imageUrl = item.path("hdurl").asText("");
            if (imageUrl.isBlank()) imageUrl = item.path("url").asText("");
            if (imageUrl.isBlank()) throw new IllegalStateException("NASA không trả về đường dẫn ảnh.");
            String date = item.path("date").asText("");
            if (date.isBlank()) throw new IllegalStateException("NASA không trả về ngày của ảnh.");
            String title = item.path("title").asText("Bức ảnh của ngày");

            ApodImage image = feedbackService.registerImage(date, title, imageUrl);
            model.addAttribute("title", title);
            model.addAttribute("date", date);
            model.addAttribute("imageUrl", imageUrl);
            model.addAttribute("explanation", Jsoup.parse(item.path("explanation").asText("")).text());
            model.addAttribute("credit", Jsoup.parse(item.path("credit").asText("")).text());
            model.addAttribute("apodImage", image);
            model.addAttribute("comments", feedbackService.findComments(date));
        } catch (Exception e) {
            model.addAttribute("error", "Không tải được ảnh từ NASA: " + e.getMessage());
        }
        return "apod";
    }

    @PostMapping("/apod/rate")
    public String rate(@RequestParam String date, @RequestParam int rating, RedirectAttributes redirect) {
        try {
            feedbackService.rate(date, rating);
            redirect.addFlashAttribute("feedbackMessage", "Cảm ơn bạn đã đánh giá bức ảnh!");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("feedbackError", e.getMessage());
        }
        return "redirect:/apod";
    }

    @PostMapping("/apod/comment")
    public String comment(@RequestParam String date, @RequestParam String author,
                          @RequestParam String content, RedirectAttributes redirect) {
        try {
            feedbackService.comment(date, author, content);
            redirect.addFlashAttribute("feedbackMessage", "Bình luận của bạn đã được đăng.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("feedbackError", e.getMessage());
        }
        return "redirect:/apod";
    }
}
