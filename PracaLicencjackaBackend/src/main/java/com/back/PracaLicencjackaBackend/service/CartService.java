package com.back.PracaLicencjackaBackend.service;


import com.back.PracaLicencjackaBackend.exceptions.CartException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.Cart;
import com.back.PracaLicencjackaBackend.models.ProductDtoSec;

import java.util.List;

public interface CartService {

	public String addProductToCart(Integer userId, Integer quantity, Integer productId) throws UserException, CartException;
	
	public List<ProductDtoSec> getAllProduct(Integer userId) throws UserException,CartException;
	
    public String removeProductFromCart(Integer productId, Integer userId) throws UserException,CartException;
	
    public ProductDtoSec updateQuantity(Integer productId,Integer quantity,Integer userid) throws UserException,CartException;

    public Cart removeAllProduct(Integer userId) throws UserException,CartException;
    
}
