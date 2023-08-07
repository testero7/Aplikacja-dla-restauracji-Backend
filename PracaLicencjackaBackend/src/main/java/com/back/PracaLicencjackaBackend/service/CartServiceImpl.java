package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.exceptions.CartException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.Cart;
import com.back.PracaLicencjackaBackend.models.Product;
import com.back.PracaLicencjackaBackend.models.ProductDtoSec;
import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.repository.CartRepository;
import com.back.PracaLicencjackaBackend.repository.ProductRepository;
import com.back.PracaLicencjackaBackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public String addProductToCart(Integer userId, Integer quantity, Integer productId) throws CartException {
    Optional<User> user1 = userRepository.findById(userId);
	Optional<Product> product1 = productRepository.findById(productId);

	if(product1.isPresent() && user1.isPresent()){
		User user = user1.get();
		Product product = product1.get();
		
		if(product.getQuantity()< quantity || quantity == 0) {
			throw new CartException("Nie ma już tego produktu");
		}
		Cart userCart = user.getCart();
		  ProductDtoSec prodto2= new ProductDtoSec();
          prodto2.setProductId(productId);
          prodto2.setImagePath(product.getImagePath());
          prodto2.setProductName(product.getProductName());
          prodto2.setQuantity(quantity);
          prodto2.setPrice(product.getPrice());
		if(userCart != null) {
		List<ProductDtoSec> list = userCart.getCartproducts();
		for(int i=0;i<list.size();i++) {
			if(productId == list.get(i).getProductId()) {
			   return "Produkt już został dodany do koszyka!";
			}
		}
		userCart.getCartproducts().add(prodto2);
		}
		else {
			userCart = new Cart();
			userCart.getCartproducts().add(prodto2);
		    userCart.setUser(user);
		    user.setCart(userCart);

		}
		cartRepository.save(userCart);
		
		return "Dodano produkt do koszyka";
		}
	  	throw new CartException("Nie znaleziono userid lub productid");
	}

	@Override
	public List<ProductDtoSec> getAllProduct(Integer userId) throws UserException, CartException {

        Optional<User> user = userRepository.findById(userId);
       if(user.isPresent()== false) {
    	   throw new UserException("Nie znaleziono takiego użytkownika!");
       }
        Cart cart = user.get().getCart();
        if(cart == null) {
        	throw new CartException("Najpierw dodaj produkt do koszyka!");
        }
			if(cart.getCartproducts().size() == 0) {
				throw  new CartException("Koszyk jest pusty!");
			}
			return cart.getCartproducts();
	}

	@Override
	public String removeProductFromCart(Integer productId, Integer userId) throws UserException, CartException {
	    
	           User user = userRepository.findById(userId).get();
	           Cart cart = user.getCart();
	           if(cart == null) {
	        	   throw new CartException("Najpierw dodaj produkt do koszyka!");
	           }
	           List<ProductDtoSec> products = cart.getCartproducts();
	           if(products.size() == 0) {
	        	   throw new CartException("Najpierw dodaj produkt do koszyka!");
	           }
	          boolean flag = true;
	           for(int i =0;i<products.size();i++) {
	        	   if(productId == products.get(i).getProductId()) {
	        		   products.remove(i);
	        		   flag = false;
	        		   break;
	        	   }
	           }
	           if(flag) {
	        	   throw new CartException("Produkt nie został dodany do koszyka!");
	           }
	           cart.setCartproducts(products);
	           cartRepository.save(cart);
	           return "Produkt został usunięty z koszyka!";

	}

	@Override
	public ProductDtoSec updateQuantity(Integer productId,Integer quantity, Integer userid)
			throws CartException {

		User user = userRepository.findById(userid).get();
		Cart cart = user.getCart();
		if(cart == null) {
			throw new CartException("Najpierw dodaj produkt do koszyka!");
		}
		List<ProductDtoSec> products = cart.getCartproducts();
		   if(products.size() == 0) {
			   throw new CartException("Najpierw dodaj produkt do koszyka!");
		   }

		 Product product = productRepository.findById(productId).get();
		 if(product == null) {
			 throw new CartException("Nie znaleziono produktu o takim id "+ productId);
		 }
		 ProductDtoSec productDtoSec = null;
		   for(int i =0;i<products.size();i++) {
			   if(productId == products.get(i).getProductId()) {

				   if(quantity>product.getQuantity() || quantity == 0) {
					   throw new CartException("Nie ma już tego produktu!");
				   }

				   products.get(i).setQuantity(quantity);
				   productDtoSec = products.get(i);
				   System.out.println(productDtoSec);
				   break;
			   }
		   }
		   cart.setCartproducts(products);
		   cartRepository.save(cart);

		return productDtoSec;

	}

	@SuppressWarnings("unused")
	@Override
	public Cart removeAllProduct( Integer userId) throws UserException, CartException {

		User user = userRepository.findById(userId).get();
		Cart cart = user.getCart();
		if(user == null) {
			throw new CartException("Najpierw dodaj produkt do koszyka!");
		}
		List<ProductDtoSec> products = cart.getCartproducts();
		   if(products.size() == 0) {
			   throw new CartException("Najpierw dodaj produkt do koszyka!");
		   }
		   cart.setCartproducts(new ArrayList<>());
		   return cartRepository.save(cart);
	}

}
