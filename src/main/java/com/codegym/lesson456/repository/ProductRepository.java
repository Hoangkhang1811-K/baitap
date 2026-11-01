package com.codegym.lesson456.repository;
import com.codegym.lesson456.model.Product;
import jakarta.persistence.*; import org.springframework.stereotype.Repository; import java.util.List;
@Repository public class ProductRepository {
    @PersistenceContext private EntityManager em;
    public List<Product> findAll(){return em.createQuery("select p from Product p order by p.id desc",Product.class).getResultList();}
    public Product findById(Long id){return em.find(Product.class,id);}
    public Product save(Product p){return em.merge(p);}
}
