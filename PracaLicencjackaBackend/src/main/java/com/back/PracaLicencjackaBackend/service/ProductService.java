package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.exceptions.ProductException;
import com.back.PracaLicencjackaBackend.models.Product;
import com.back.PracaLicencjackaBackend.models.ProductDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {

	public Product createProduct(ProductDTO product) throws ProductException;

	public String removeProduct(@Param("productId") Integer productId) throws ProductException;
	
	public Product updateProduct(ProductDTO product, Integer productId) throws ProductException;
	
	public Product productById(Integer productId) throws ProductException;

	public List<Product> getAllProduct() throws ProductException;


}
