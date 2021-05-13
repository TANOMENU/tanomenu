package tanomenu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tanomenu.models.Product;
import tanomenu.repository.ProductRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users/{uuid}/restaurant/{uuid}")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<Product> setProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(productRepository.findId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

}
