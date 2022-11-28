package com.jbk.api.controller;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.entity.Product;
import com.jbk.api.service.ProductService;
import com.mysql.fabric.Response;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping(value = "/save_product")
	public ResponseEntity<Boolean> saveProduct(@RequestBody Product product) {

		boolean isAdded = service.saveProduct(product);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/get_product_by_id/{productid}")
	public ResponseEntity<Product> getProductById(@PathVariable int productid) {
		Product product = service.getProductById(productid);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/get_all_products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> allProducts = service.getAllProducts();
		if (!allProducts.isEmpty()) {
			return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@DeleteMapping(value = "/delete_product")
	public ResponseEntity<Boolean> getAllProducts(@RequestParam int productid) {
		boolean isDeleted = service.deleteProduct(productid);

		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);

	}

	@PutMapping(value = "/update_product")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product) {

		boolean isUpdated = service.updateProduct(product);

		return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);

	}

	
	
	///////////////////////////////////////////////
	
	@GetMapping(value = "/sort_products_by_id_asc")
	public ResponseEntity<List<Product>> sortProductsById_ASC() {
		List<Product> sortedList = service.sortProductsById_ASC();
		if (!sortedList.isEmpty()) {
			return new ResponseEntity<List<Product>>(sortedList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	} 
	
	@GetMapping(value = "/sort_products_by_name_DESC")
	public ResponseEntity<List<Product>> sortProductsByName_DESC() {
		List<Product> sortedList = service.sortProductsByName_DESC();
		if (!sortedList.isEmpty()) {
			return new ResponseEntity<List<Product>>(sortedList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	} 
	
	@GetMapping(value = "/get_max_price_products")
	public ResponseEntity<List<Product>> getMaxPriceProducts() {

		List<Product> maxPriceProducts = service.getMaxPriceProducts();

		return new ResponseEntity<List<Product>>(maxPriceProducts, HttpStatus.OK);

	}
	
	@GetMapping(value = "/count_sum_of_product_price")
	public ResponseEntity<Double> countSumOfProductPrice() {

		double sumOfProducts = service.countSumOfProductPrice();

		return new ResponseEntity<Double>(sumOfProducts, HttpStatus.OK);

	}
	
	@GetMapping(value = "/get_total_count_of_products")
	public ResponseEntity<Long> getTotalCountOfProducts() {

		long countOfProducts = service.getTotalCountOfProducts();

		return new ResponseEntity<Long>(countOfProducts, HttpStatus.OK);

	}
	
	////////////////////////////
	
	@PostMapping(value = "/uploadsheet")
	public ResponseEntity<String> uploadSheet(@RequestParam MultipartFile file,HttpSession session){
		
	String msg = service.uploadSheet(file, session);
		
		
		return null;
		
	}
	

}
