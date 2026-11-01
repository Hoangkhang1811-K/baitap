package com.codegym.lesson456.repository;
import com.codegym.lesson456.model.Customer;
import jakarta.persistence.*; import org.springframework.stereotype.Repository; import java.util.List;
@Repository public class CustomerRepository {
    @PersistenceContext private EntityManager em;
    public List<Customer> findAll(){return em.createQuery("select c from Customer c order by c.id",Customer.class).getResultList();}
    public Customer findById(long id){return em.find(Customer.class,id);}
    public Customer save(Customer c){return em.merge(c);}
    public void delete(long id){Customer c=findById(id);if(c!=null)em.remove(c);}
}
