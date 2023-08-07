package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.exceptions.OrderException;
import com.back.PracaLicencjackaBackend.exceptions.ProductException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.Orders;
import com.back.PracaLicencjackaBackend.models.Product;
import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.repository.UserRepository;
import com.back.PracaLicencjackaBackend.service.OrderService;
import com.back.PracaLicencjackaBackend.service.ProductService;
import com.back.PracaLicencjackaBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
    private UserService userService;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/viewAll")
	public ResponseEntity<List<User>> findAllUser() throws UserException {
		List<User> users = userService.viewUserAll();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}

	@GetMapping("/viewById/{userId}")
	public ResponseEntity<User> findUserById(@PathVariable Integer userId) throws UserException {
		User user = userService.viewUser(userId);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	@PatchMapping("/changeUserRole/{userId}")
	public  ResponseEntity<User> changeUserRole(@PathVariable Integer userId, @Valid @RequestBody Map<String, Object> fields, Errors errors){
		Optional<User> users = userRepository.findById(userId);
		User user1 = users.get();
		if(fields.containsKey("role")) user1.setRole((String) fields.get("role"));
		userRepository.save(user1);
		return new ResponseEntity<User>(user1,HttpStatus.OK);
	}

    @DeleteMapping("/removeProduct/{productId}")
	public ResponseEntity<String> removeProduct(@PathVariable Integer productId) throws ProductException {
		String message = productService.removeProduct(productId);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}

	@GetMapping("/getProductById/{productId}") 
	public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws ProductException{
		Product product = productService.productById(productId);
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}

	@GetMapping("/getAllOrders")
	public ResponseEntity<List<Orders>> getAllOrders() throws OrderException, UserException {
		List<Orders> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Orders>>(orders,HttpStatus.OK);
	}



}
