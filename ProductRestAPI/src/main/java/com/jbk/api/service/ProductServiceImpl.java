package com.jbk.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.api.dao.ProductDao;
import com.jbk.api.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	@Override

	public boolean saveProduct(Product product) {

		boolean isAdded = dao.saveProduct(product);

		return isAdded;
	}

	@Override
	public Product getProductById(int productId) {

		return dao.getProductById(productId);
	}

	@Override
	public List<Product> getAllProducts() {

		return dao.getAllProducts();
	}

	@Override
	public boolean deleteProduct(int productId) {

		return dao.deleteProduct(productId);
	}

	@Override
	public boolean updateProduct(Product product) {

		return dao.updateProduct(product);
	}

	@Override
	public List<Product> getMaxPriceProducts() {

		return dao.getMaxPriceProducts();
	}

	@Override
	public List<Product> sortProductsById_ASC() {

		return dao.sortProductsById_ASC();
	}

	@Override
	public List<Product> sortProductsByName_DESC() {

		return dao.sortProductsByName_DESC();
	}

	@Override
	public double countSumOfProductPrice() {

		return dao.countSumOfProductPrice();
	}

	@Override
	public long getTotalCountOfProducts() {

		return dao.getTotalCountOfProducts();
	}

	public List<Product> readExcel(String path) {
		Product product = null;
		List<Product> list = new ArrayList<>();
		try {

			FileInputStream fis = new FileInputStream(new File(path));

			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rows = sheet.rowIterator();
			int cnt = 0;

			while (rows.hasNext()) {
				Row row = rows.next();
				product = new Product();
				if (cnt == 0) {
					cnt = cnt + 1;
					continue;
				}

				
				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {

					Cell cell = cells.next();
					int col = cell.getColumnIndex();
					
					switch (col) {
					case 0: {
						product.setProductId((int) cell.getNumericCellValue());
						
						break;
					}

					case 1: {
						product.setProductName(cell.getStringCellValue());
						break;
					}

					case 2: {
						product.setProductQty((int) cell.getNumericCellValue());
						break;
					}

					case 3: {
						product.setProductPrice(cell.getNumericCellValue());
						break;
					}

					case 4: {
						product.setProductType(cell.getStringCellValue());
						break;
					}

					default:
						break;
					}

				}

				list.add(product);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public String uploadSheet(MultipartFile file, HttpSession session) {

		String path = session.getServletContext().getRealPath("/uploaded");
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);

		try {
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName));
			fos.write(data);

			List<Product> list = readExcel(path + File.separator + fileName);

			/*
			 * for (Product product : list) { System.out.println(product); }
			 */
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
