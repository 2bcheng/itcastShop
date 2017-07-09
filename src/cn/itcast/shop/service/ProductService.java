package cn.itcast.shop.service;

import java.util.List;

import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;

public interface ProductService {

	List<Product> findHotProductList()  ;

	List<Product> findnewProductList();

	PageBean<Product> findproductsByCid(String cid,int currentPage,int currentTotal);

	Product findProductByPid(String pid);

	List<Product> getAllProducts();

	int add(Product product);

	PageBean<Product> getAllProducts(int currentCount, int currentPage,Product product);

}
