package com.codegym.lesson456.service;
import com.codegym.lesson456.model.Product; import com.codegym.lesson456.repository.ProductRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service @Transactional public class ProductService {
    private final ProductRepository repository; public ProductService(ProductRepository r){repository=r;}
    @Transactional(readOnly=true) public List<Product> findAll(){return repository.findAll();}
    public Product save(Product p){return repository.save(p);}
    public void rate(Long id, int rating){
        if(rating < 1 || rating > 5) throw new IllegalArgumentException("Đánh giá phải từ 1 đến 5 sao");
        Product product=repository.findById(id);
        if(product == null) throw new IllegalArgumentException("Không tìm thấy ảnh");
        product.setRatingSum(product.getRatingSum()+rating);
        product.setRatingCount(product.getRatingCount()+1);
    }
}
