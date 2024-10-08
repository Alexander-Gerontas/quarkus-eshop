package gr.alg.service;

import gr.alg.dto.request.CreateProductDto;
import gr.alg.dto.response.ProductResponseDto;
import gr.alg.mappers.ProductMapper;
import gr.alg.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public void createProduct(CreateProductDto productDto) {
    var newProduct = productMapper.fromDto(productDto);
    newProduct.setCreatedAt(LocalDate.now());

    productRepository.persist(newProduct);
  }

  public ProductResponseDto findProductById(Long id) {
    var productEntity = productRepository.findById(id);

    return productMapper.toResponseDto(productEntity);
  }
}
