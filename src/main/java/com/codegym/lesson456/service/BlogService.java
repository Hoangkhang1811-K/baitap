package com.codegym.lesson456.service;
import com.codegym.lesson456.model.Blog; import com.codegym.lesson456.repository.BlogRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service @Transactional public class BlogService {
    private final BlogRepository repository; public BlogService(BlogRepository r){repository=r;}
    @Transactional(readOnly=true) public List<Blog> findAll(){return repository.findAll();}
    @Transactional(readOnly=true) public Blog findById(long id){return repository.findById(id);}
    public Blog save(Blog b){return repository.save(b);} public void delete(long id){repository.delete(id);}
}
