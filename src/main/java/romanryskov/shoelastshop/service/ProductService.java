package romanryskov.shoelastshop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import romanryskov.shoelastshop.model.entity.Product;
import romanryskov.shoelastshop.model.enums.ProductType;
import romanryskov.shoelastshop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with " + id + " not found"));
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setDigitalPrice(updatedProduct.getDigitalPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setCategory(updatedProduct.getCategory());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setVideoUrl(updatedProduct.getVideoUrl());
        product.setViewer_url(updatedProduct.getViewer_url());
        product.setViewerFolder(updatedProduct.getViewerFolder());
        product.setViewerEmbedCode(updatedProduct.getViewerEmbedCode());
        product.setProductType(updatedProduct.getProductType());

        return product;

    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByType(ProductType productType) {
        return productRepository.findByProductType(productType);
    }
}