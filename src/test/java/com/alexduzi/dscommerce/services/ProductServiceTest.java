package com.alexduzi.dscommerce.services;

import com.alexduzi.dscommerce.dto.ProductDTO;
import com.alexduzi.dscommerce.entities.Category;
import com.alexduzi.dscommerce.entities.Product;
import com.alexduzi.dscommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private Product product;
    private ProductDTO productDTO;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;

        product = new Product(existingId, "Playstation", "video game", 2000.0, "url");
        product.getCategories().add(new Category(1L, "Category 1"));
        productDTO = new ProductDTO(product);

        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        when(repository.save(any())).thenReturn(product);
        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    void shouldProductServiceSaveProduct() {
        ProductService serviceSpy = spy(productService);
        doNothing().when(serviceSpy).validateData(productDTO);

        ProductDTO result = serviceSpy.insert(productDTO);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
    }
}