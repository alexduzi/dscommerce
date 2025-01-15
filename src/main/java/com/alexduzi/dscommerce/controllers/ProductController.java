package com.alexduzi.dscommerce.controllers;

import com.alexduzi.dscommerce.dto.ProductDTO;
import com.alexduzi.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        ProductDTO result = service.findById(id);

        return result;
    }

    @GetMapping
    public Page<ProductDTO> findAll(Pageable Pageable) {
        return service.findAll(Pageable);
    }

    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO productDTO) {
        return service.insert(productDTO);
    }
}
