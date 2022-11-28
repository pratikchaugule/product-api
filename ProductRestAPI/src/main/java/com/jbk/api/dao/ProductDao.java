package com.jbk.api.dao;

import java.util.List;

import com.jbk.api.entity.Product;

public interface ProductDao {
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
	
	public String excelToDb(List<Product> list);

}
