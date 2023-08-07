package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.exceptions.CartException;
import com.back.PracaLicencjackaBackend.exceptions.OrderException;
import com.back.PracaLicencjackaBackend.exceptions.ProductException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.*;
import com.back.PracaLicencjackaBackend.repository.UserRepository;
import com.back.PracaLicencjackaBackend.service.CartService;
import com.back.PracaLicencjackaBackend.service.OrderService;
import com.back.PracaLicencjackaBackend.service.ProductService;
import com.back.PracaLicencjackaBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
	UserService userService;

    @Autowired
	CartService cartService;

	@Autowired
	OrderService orderService;

	@Autowired
	ProductService productService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws UserException {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		User user = userService.createUser(userDTO);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDTO user, BindingResult bindingResult) throws UserException {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		User updatedUser = userService.updateUser(user, userId);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	@GetMapping("/cart/{userId}/{quantity}/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable Integer userId, @PathVariable Integer quantity, @PathVariable Integer productId ) throws CartException, UserException {
	    	String mess = cartService.addProductToCart(userId,quantity, productId);
	    	return new ResponseEntity<String>(mess, HttpStatus.OK);
	}

	@GetMapping("/getAllProductAddedInCart/{userId}")
	public ResponseEntity<List<ProductDtoSec>> getAllProductAddedToCart(@PathVariable Integer userId) throws CartException, UserException {
	   List<ProductDtoSec> products = cartService.getAllProduct(userId);
	   return new ResponseEntity<List<ProductDtoSec>>(products,HttpStatus.OK);
	}

	@DeleteMapping("/removeProductFromCart/{productId}/{userId}")
	public ResponseEntity<String> removeProductFromCart(@PathVariable Integer productId,@PathVariable Integer userId) throws UserException, CartException{
		String mess = cartService.removeProductFromCart(productId, userId);
		return new ResponseEntity<String>(mess, HttpStatus.OK);
	}
	
	@GetMapping("/updatingQuantity/{productId}/{quantity}/{userId}")
	public ResponseEntity<ProductDtoSec> updateQuantityOfProduct(@PathVariable Integer productId,@PathVariable Integer quantity,@PathVariable Integer userId) throws UserException,CartException{
		ProductDtoSec productDtoSec = cartService.updateQuantity(productId, quantity, userId);
		return new  ResponseEntity<ProductDtoSec>(productDtoSec,HttpStatus.OK);
	}
	
	@DeleteMapping("/removeAllProductfromCart/{userId}")
	public ResponseEntity<Cart> removeAllProduct(@PathVariable Integer userId) throws UserException, CartException {
		Cart cart = cartService.removeAllProduct(userId);
		return new  ResponseEntity<Cart>(cart,HttpStatus.OK);
	}

	@DeleteMapping("/cancelOrder/{orderId}")
	public ResponseEntity<String> CancelOrder(@PathVariable Integer orderId) throws OrderException, UserException {
		String mess = orderService.cancelOrder(orderId);
		return new ResponseEntity<String>(mess,HttpStatus.OK);
	}

	@GetMapping("/getAllProduct") 
	public ResponseEntity<List<Product>> getAllProduct() throws ProductException {
		List<Product> allProduct = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(allProduct,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId) throws UserException {
		String deletedUser = userService.deleteUser(userId);
		return new ResponseEntity<String>(deletedUser,HttpStatus.OK);
	}
	
	@GetMapping("/getAllOrdersByUser/{userId}")
	public ResponseEntity<List<Orders>> getAllOrders(@PathVariable Integer userId) throws OrderException, UserException {
		List<Orders> orders = orderService.getAllOrdersByUser(userId);
		return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
	}

}
