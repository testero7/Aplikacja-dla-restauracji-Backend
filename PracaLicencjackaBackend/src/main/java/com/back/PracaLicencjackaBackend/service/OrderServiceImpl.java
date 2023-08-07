package com.back.PracaLicencjackaBackend.service;


import com.back.PracaLicencjackaBackend.exceptions.OrderException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.*;
import com.back.PracaLicencjackaBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@SuppressWarnings("unused")
	@Override
	public Orders OrderProducts(Integer userId, AddressDTO address) throws OrderException, UserException {
	
		Optional<User> user1 = userRepository.findById(userId);
		
		if(user1.isPresent()) {
			User user = user1.get();
			Cart cart = user.getCart();
			if(cart.getCartproducts().size()==0) {
				throw new OrderException("Najpierw dodaj produkt do koszyka!");
			}
			if(cart == null) {
				throw new OrderException("Najpierw dodaj produkt do koszyka!");
			}
		Orders orders = new Orders();
		User user2 = cart.getUser();
		Address address1 = new Address();
		address1.setBuildingNo(address.getBuildingNo());
		address1.setCity(address.getCity());
		address1.setCountry(address.getCountry());
		address1.setPincode(address.getPincode());
		address1.setStreet(address.getStreet());
		address1 = addressRepository.save(address1);
		user2.setAddress(address1);
		userRepository.save(user2);
	     
		orders.setAddress(user2.getAddress());
		orders.setOrderDate(LocalDate.now());
		orders.setUser(user2);
		List<ProductDtoSec> products= new ArrayList<>();
		for(ProductDtoSec p : cart.getCartproducts()) {
			products.add(p);
		}
		orders.setProducts(products);
		orders.setOrderStatus("ZATWIERDZONE");
		orderRepository.save(orders);
		for(ProductDtoSec p : cart.getCartproducts()) {
			Product product = productRepository.findById(p.getProductId()).get();
			product.setQuantity(product.getQuantity()-p.getQuantity());
			productRepository.save(product);
		}
		cart.getCartproducts().clear();
		cartRepository.save(cart);
		return orders;
		}
		throw new OrderException("Nie znaleziono użytkownika o id: "+user1.get().getUserId());
	}

	@Override
	public String cancelOrder(Integer orderId) throws OrderException{
	        	Orders orders = orderRepository.findById(orderId).get();
	        	if(orders ==  null) {
	        	   throw new OrderException("Nie znaleziono zamówienia o id: "+orderId);
	        	}
	        	if(!orders.getOrderStatus().equals("ANULOWANE")) {
	        	orders.setOrderDate(LocalDate.now());
	        	orders.setOrderStatus("ANULOWANE");
	        	List<ProductDtoSec> products = orders.getProducts();
	        	for(ProductDtoSec p : products) {
	        		Product product = productRepository.findById(p.getProductId()).get();
	        		product.setQuantity(product.getQuantity()+p.getQuantity());
	        		productRepository.save(product);
	        	}
	        	orderRepository.save(orders);

	        	return "Udało się anulować zamówienie!";
	        	}else {
	        		if(orders.getOrderStatus().equals("ANULOWANE")) {
	        		  return "Udało się anulować zamówienie!";
	        		}else {
						return "Wystąpił błąd!";
					}
	        	}
	}

	@Override
	public Orders getOrderById(Integer orderId) throws OrderException{

	        	Optional<Orders> orders = orderRepository.findById(orderId);
	        	if(orders.isPresent()) {
	        		Orders orders1 = orders.get();
	        		return orders1;
	        	}else {
	        		throw new OrderException("Nie znaleziono zamówienie o id: " + orderId);
	        	}

	}

	@Override
	public List<Orders> getAllOrdersByUser(Integer userId) throws OrderException{
		     List<Orders> ordersByUser = new ArrayList<>();
	        	List<Orders> orders = orderRepository.findAll();
	        	for(Orders o : orders) {
	        		if(o.getUser().getUserId() == userId) {
	        			ordersByUser.add(o);
	        		}
	        	}
	        	if(ordersByUser.size()== 0) {
	        		throw new OrderException("Nie znaleziono żadnych zamówień!");
	        	}
	        	return ordersByUser;
	        }
	@Override
	public List<Orders> getAllOrders() throws OrderException{
		List<Orders> orders = orderRepository.findAll();
		if(orders.size()== 0) {
			throw new OrderException("Nie znaleziono żadnych zamówień!");
		}
		return orders;
	}
}
