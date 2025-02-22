package com.exm.ordermanagement.service;

import com.exm.ordermanagement.dto.ProductRequestDTO;
import com.exm.ordermanagement.entity.Category;
import com.exm.ordermanagement.entity.Product;
import com.exm.ordermanagement.repository.CategoryRepository;
import com.exm.ordermanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private  ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(ProductRequestDTO requestDTO) {

        Product product = new Product();

        Optional<Category> category = categoryRepository.findById((long)requestDTO.getCategoryId());

        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setImage(requestDTO.getImage());
        product.setCategory(category.get());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequestDTO updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setCategory(categoryRepository.findById((long)updatedProduct.getCategoryId()).get());
                    existingProduct.setImage(updatedProduct.getImage());
                    existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
