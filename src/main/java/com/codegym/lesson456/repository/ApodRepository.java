package com.codegym.lesson456.repository;

import com.codegym.lesson456.model.ApodComment;
import com.codegym.lesson456.model.ApodImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ApodRepository {
    @PersistenceContext private EntityManager em;

    public ApodImage findImage(String date) { return em.find(ApodImage.class, date); }
    public ApodImage saveImage(ApodImage image) { return em.merge(image); }
    public void saveComment(ApodComment comment) { em.persist(comment); }
    public List<ApodComment> findComments(String date) {
        return em.createQuery("select c from ApodComment c where c.image.date = :date order by c.createdAt desc", ApodComment.class)
                .setParameter("date", date).getResultList();
    }
}
