package com.magadiflo.products.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magadiflo.products.entity.Product;
import com.magadiflo.products.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	private final IProductRepository productRepository;

	public ProductServiceImpl(IProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return (List<Product>) this.productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Integer id) {
		return this.productRepository.findById(id).orElseGet(() -> null);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		this.productRepository.deleteById(id);
	}

}
