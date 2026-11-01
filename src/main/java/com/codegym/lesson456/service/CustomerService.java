package com.codegym.lesson456.service;
import com.codegym.lesson456.model.Customer; import com.codegym.lesson456.repository.CustomerRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service @Transactional public class CustomerService {
    private final CustomerRepository repository; public CustomerService(CustomerRepository r){repository=r;}
    @Transactional(readOnly=true) public List<Customer> findAll(){return repository.findAll();}
    @Transactional(readOnly=true) public Customer findById(long id){return repository.findById(id);}
    public Customer save(Customer c){return repository.save(c);} public void delete(long id){repository.delete(id);}
}
