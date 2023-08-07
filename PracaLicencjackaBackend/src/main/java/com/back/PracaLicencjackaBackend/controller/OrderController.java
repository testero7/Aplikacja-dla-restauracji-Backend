package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.exceptions.OrderException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.AddressDTO;
import com.back.PracaLicencjackaBackend.models.Orders;
import com.back.PracaLicencjackaBackend.repository.OrderRepository;
import com.back.PracaLicencjackaBackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/orderProduct/{userId}")
	public ResponseEntity<Orders> Order(@PathVariable Integer userId, @RequestBody AddressDTO addressDTO) throws OrderException, UserException {
		Orders orders = orderService.OrderProducts(userId,addressDTO);
		return new ResponseEntity<Orders>(orders,HttpStatus.OK);
	}

	@GetMapping("/getOrderById/{orderId}")
	public ResponseEntity<Orders> getOrderById(@PathVariable Integer orderId) throws OrderException, UserException {
		Orders orders = orderService.getOrderById(orderId);
		return new ResponseEntity<Orders>(orders,HttpStatus.OK);
	}

	@PatchMapping("/changeOrderStatus/{orderId}")
	public  ResponseEntity<Orders> changeOrderStatus(@PathVariable Integer orderId , @Valid @RequestBody Map<String, Object> fields, Errors errors) throws OrderException, UserException {
		Optional<Orders> orders = orderRepository.findById(orderId);
		Orders orders1 = orders.get();
		if(fields.containsKey("orderStatus")) orders1.setOrderStatus((String) fields.get("orderStatus"));
		orderRepository.save(orders1);
		return new ResponseEntity<Orders>(orders1,HttpStatus.OK);
	}

}