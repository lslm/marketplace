package dev.lslm.marketplace.services;

import dev.lslm.marketplace.entities.Product;
import dev.lslm.marketplace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product find(long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
