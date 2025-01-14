package com.alexduzi.dscommerce.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexduzi.dscommerce.dto.ProductDTO;
import com.alexduzi.dscommerce.entities.Product;
import com.alexduzi.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id).orElseThrow();

		return convertToDto(product);
	}

	private ProductDTO convertToDto(Product product) {
		ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
		return productDto;
	}
}
