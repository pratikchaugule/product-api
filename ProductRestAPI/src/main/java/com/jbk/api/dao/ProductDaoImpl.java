package com.jbk.api.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jbk.api.entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean saveProduct(Product product) {
		// get session factory

		boolean isAdded = false;
		Session session = sessionFactory.openSession();
		try {
			// get session from session factory
			Product prd = session.get(Product.class, product.getProductId());
			if (prd == null) {
				Transaction transaction = session.beginTransaction();
				session.save(product);
				transaction.commit();
				isAdded = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isAdded;

	}

	@Override
	public Product getProductById(int productId) {
		Session session = sessionFactory.openSession();
		Product product = null;
		try {
			product = session.get(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);

			criteria.addOrder(Order.asc("productName"));

			list = criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean deleteProduct(int productId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isDeleted = false;
		try {
			Product product = session.get(Product.class, productId);
			if (product != null) {
				session.delete(product);
				transaction.commit();
				isDeleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDeleted;
	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isUpdated = false;
		try {
			Product prd = session.get(Product.class, product.getProductId());
			session.evict(prd);
			if (prd != null) {
				session.update(product);
				transaction.commit();
				isUpdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isUpdated;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		Session session = sessionFactory.openSession();
		List<Product> maxProducts = null;
		double maxPrice = 0;
		try {
			Criteria maxcriteria = session.createCriteria(Product.class);
			Criteria eqcriteria = session.createCriteria(Product.class);
			maxcriteria.setProjection(Projections.max("productPrice"));
			List<Double> list = maxcriteria.list();
			maxPrice = list.get(0);

			eqcriteria.add(Restrictions.eq("productPrice", maxPrice));
			maxProducts = eqcriteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return maxProducts;
	}

	@Override
	public List<Product> sortProductsById_ASC() {
		Session session = sessionFactory.openSession();
		List<Product> sortedList = null;

		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.asc("productId"));
			sortedList = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return sortedList;
	}

	@Override
	public List<Product> sortProductsByName_DESC() {
		Session session = sessionFactory.openSession();
		List<Product> sortedList = null;

		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.desc("productName"));
			sortedList = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return sortedList;
	}

	@Override
	public double countSumOfProductPrice() {
		Session session = sessionFactory.openSession();
		double sumOfProductsPrice = 0;

		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.sum("productPrice"));

			List<Double> list = criteria.list();
			if (!list.isEmpty()) {
				sumOfProductsPrice = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return sumOfProductsPrice;
	}

	@Override
	public long getTotalCountOfProducts() {
		Session session = sessionFactory.openSession();
		long countOfProductsPrice = 0;

		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.rowCount());
			List<Long> list = criteria.list();
			if (!list.isEmpty()) {
				countOfProductsPrice = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return countOfProductsPrice;
	}

	@Override
	public String excelToDb(List<Product> list) {

		for (Product product : list) {
		saveProduct(product);
		}
		return null;

	}
}
