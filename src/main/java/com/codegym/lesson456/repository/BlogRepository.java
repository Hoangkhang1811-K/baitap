package com.codegym.lesson456.repository;
import com.codegym.lesson456.model.Blog;
import jakarta.persistence.*; import org.springframework.stereotype.Repository; import java.util.List;
@Repository public class BlogRepository {
    @PersistenceContext private EntityManager em;
    public List<Blog> findAll(){return em.createQuery("select b from Blog b order by b.createdAt desc",Blog.class).getResultList();}
    public Blog findById(long id){return em.find(Blog.class,id);}
    public Blog save(Blog b){return em.merge(b);}
    public void delete(long id){Blog b=findById(id);if(b!=null)em.remove(b);}
}
