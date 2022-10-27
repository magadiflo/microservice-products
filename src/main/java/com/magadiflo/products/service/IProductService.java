package com.magadiflo.products.service;

import java.util.List;

import com.magadiflo.products.entity.Product;

public interface IProductService {

	List<Product> findAll();

	Product findById(Integer id);

	Product save(Product product);

	void deleteById(Integer id);

}
