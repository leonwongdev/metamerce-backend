package dev.leonwong.service;


import dev.leonwong.model.Product;
import dev.leonwong.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);


        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            // Handle the case when the product is not found
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(product.getId());

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setCreationDate(product.getCreationDate());

            return productRepository.save(existingProduct);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + product.getId());
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
    }
}
