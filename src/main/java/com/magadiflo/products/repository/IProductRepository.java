package com.magadiflo.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.magadiflo.products.entity.Product;

public interface IProductRepository extends CrudRepository<Product, Integer> {

}
