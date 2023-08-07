package com.back.PracaLicencjackaBackend.service;


import com.back.PracaLicencjackaBackend.models.*;
import com.back.PracaLicencjackaBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.back.PracaLicencjackaBackend.exceptions.ProductException;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDtoRepository productDtoRepository;

	@Override
	public Product createProduct(ProductDTO product) throws ProductException {

		Product product1 = new Product();
		product1.setProductName(product.getProductName());
		product1.setImagePath(product.getImagePath());
		product1.setQuantity(product.getQuantity());
		product1.setSpecification(product.getSpecification());
		product1.setPrice(product.getPrice());
		Category category = new Category();
		category.setCategoryName(product.getCategoryName());
		product1.setCategory(category);
		category.getProducts().add(product1);
		productRepository.save(product1);
		return product1;
	}

	@Override
	public String removeProduct(Integer productId) throws ProductException {

		Product product =  productRepository.findById(productId).orElseThrow(()->new ProductException("Nie ma produktu o takim id: "+ productId));
		List<ProductDtoSec> productDtoSecs = productDtoRepository.findProductDtoSecByProductId(productId);

		productRepository.delete(product);
		return "Udało się usunąć produkt";

	}

	@Override
	public Product updateProduct(ProductDTO product,Integer productId) throws ProductException {
			Optional<Product> p1 = productRepository.findById(productId);
			if(p1.isPresent() == false) {
				throw new ProductException("Nie znaleziono produktu o id: "+ product.getProductId());
			}
			Product p = p1.get();
			p.setProductName(product.getProductName());
			p.setQuantity(product.getQuantity());
			p.setImagePath(product.getImagePath());
			p.setSpecification(product.getSpecification());
			p.setPrice(product.getPrice());
			Category c = p.getCategory();
			c.setCategoryName(product.getCategoryName());
			List<Product> products = c.getProducts();
			for(int i = 0;i<products.size();i++) {
				if(products.get(i).getProductId() == productId) {
					products.set(i, p);
					break;
				}
			}
			c.setProducts(products);
			p.setCategory(c);
			productRepository.save(p);
			
			return p;
		}

	@Override
	public Product productById(Integer productId) throws ProductException {
			Product product = productRepository.findById(productId).orElseThrow(()-> new ProductException("Nie znaleziono produktu o id: " + productId));
			return product;

	}

	@Override
	public List<Product> getAllProduct() throws ProductException {
           List<Product> products = productRepository.findAll();
           if(products.size()==0) {
        	   throw new ProductException("Nie ma żadnego produktu!");
           }
           return products;
	}

}
