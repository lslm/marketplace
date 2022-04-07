package dev.lslm.marketplace.controllers;

import dev.lslm.marketplace.entities.Product;
import dev.lslm.marketplace.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marketplace/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product newProduct) {
        Product createdProduct = productService.create(newProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable long id) {
        return new ResponseEntity<>(productService.find(id), HttpStatus.OK);
    }

}
