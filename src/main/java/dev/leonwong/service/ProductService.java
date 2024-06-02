package dev.leonwong.service;

import dev.leonwong.model.Product;

import java.util.List;

public interface ProductService {

    // Function to get product by id
    public Product findProductById(Long id);

    // Function to get all food
    public List<Product> findAll();

    // Function to create product
    public Product createProduct(Product product);

    // Function to update product
    public Product updateProduct(Product product);

    // Function to delete product
    public void deleteProduct(Long id);

}
