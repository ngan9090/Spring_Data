package com.example.springboottutorial.rest;

import com.example.springboottutorial.dao.ProductRepository;
import com.example.springboottutorial.model.Products;
import com.example.springboottutorial.payload.ProductPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getListProduct() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/products")
    public ResponseEntity<Void> save(@RequestBody ProductPayload payload) {
        System.out.println(payload);
        Products products = new Products(UUID.randomUUID().toString(), payload.getProductName(),
                payload.getProductLine(), payload.getProductScale(), payload.getProductVendor(),
                payload.getProductDescription(), payload.getQuantityInStock(), payload.getBuyPrice(),
                payload.getMsrp(), payload.getProductImageLink());
        productRepository.save(products);
        return ResponseEntity.ok().build();
    }

    //Delete product by id
    @PostMapping ("/delete/{productCode}")
    public ResponseEntity<Void> delete(@PathVariable("productCode") String productCode) {
        productRepository.deleteById(productCode);
        return ResponseEntity.ok().build();
    }

    //Update product by id
    @PostMapping("/update")
    public ResponseEntity<Products> updateProduct(@RequestBody Products product) {
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }
}
