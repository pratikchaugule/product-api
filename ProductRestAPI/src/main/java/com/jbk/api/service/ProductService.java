package com.jbk.api.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.entity.Product;

public interface ProductService {
	
	public boolean saveProduct(Product product);
	public Product getProductById(int productId);
	public List<Product> getAllProducts();
	public boolean deleteProduct(int productId);
	public boolean updateProduct(Product product);
	
	public List<Product> getMaxPriceProducts();
	public List<Product> sortProductsById_ASC();
	public List<Product> sortProductsByName_DESC();
	public double countSumOfProductPrice();
	public long getTotalCountOfProducts();
	
	public String uploadSheet(MultipartFile file,HttpSession session);
	
	

}
