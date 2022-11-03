package com.magadiflo.products.resource;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.magadiflo.products.entity.Product;
import com.magadiflo.products.service.IProductService;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductResource {

	@Autowired
	private Environment env; // Para obtener a partir del local.server.port el puerto aleatorio

	private final IProductService productService;

	public ProductResource(IProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<Product> productList() {
		return this.productService.findAll().stream().map(product -> {
			product.setPort(Integer.parseInt(this.env.getProperty("local.server.port")));
			return product;
		}).collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public Product showProduct(@PathVariable Integer id) throws InterruptedException {
		// ****** Simulando una falla para ver el comportamiento del CircuitBreaker
		if (id.equals(10)) {
			throw new IllegalStateException("Not found product with id = 10");
		}

		if (id.equals(7)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		// ******
		Product product = this.productService.findById(id);
		product.setPort(Integer.parseInt(this.env.getProperty("local.server.port")));
		return product;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Product saveProduct(@RequestBody Product product) {
		return this.productService.save(product);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Integer id) {
		this.productService.deleteById(id);
	}

}
