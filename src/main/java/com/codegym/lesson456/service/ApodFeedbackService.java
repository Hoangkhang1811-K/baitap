package com.codegym.lesson456.service;

import com.codegym.lesson456.model.ApodComment;
import com.codegym.lesson456.model.ApodImage;
import com.codegym.lesson456.repository.ApodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ApodFeedbackService {
    private final ApodRepository repository;
    public ApodFeedbackService(ApodRepository repository) { this.repository = repository; }

    @Transactional
    public ApodImage registerImage(String date, String title, String imageUrl) {
        ApodImage image = repository.findImage(date);
        if (image == null) image = new ApodImage(date, title, imageUrl);
        else { image.setTitle(title); image.setImageUrl(imageUrl); }
        return repository.saveImage(image);
    }

    @Transactional(readOnly = true)
    public List<ApodComment> findComments(String date) { return repository.findComments(date); }

    @Transactional
    public void rate(String date, int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Đánh giá phải từ 1 đến 5 sao.");
        ApodImage image = requireImage(date);
        image.addRating(rating);
    }

    @Transactional
    public void comment(String date, String author, String content) {
        String safeAuthor = author == null ? "" : author.trim();
        String safeContent = content == null ? "" : content.trim();
        if (safeAuthor.isEmpty() || safeAuthor.length() > 80) throw new IllegalArgumentException("Tên cần có từ 1 đến 80 ký tự.");
        if (safeContent.isEmpty() || safeContent.length() > 1000) throw new IllegalArgumentException("Bình luận cần có từ 1 đến 1000 ký tự.");
        repository.saveComment(new ApodComment(requireImage(date), safeAuthor, safeContent));
    }

    private ApodImage requireImage(String date) {
        ApodImage image = repository.findImage(date);
        if (image == null) throw new IllegalArgumentException("Không tìm thấy dữ liệu ảnh để gửi phản hồi.");
        return image;
    }
}
