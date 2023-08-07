package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.Orders;
import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.models.UserDTO;
import com.back.PracaLicencjackaBackend.repository.OrderRepository;
import com.back.PracaLicencjackaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(UserDTO user) throws UserException {
        User existingUser = userRepository.findByMobileNumber(user.getMobileNumber());
	    if(existingUser != null) throw new UserException("Użytkownik z takim numerem telefonu już jest zarejestrowany.");

	    User user1 = new User();
	    user1.setUsername(user.getUsername());
	    user1.setEmail(user.getEmail());
	    user1.setMobileNumber(user.getMobileNumber());
	    user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user1.setRole("USER");
	    userRepository.save(user1);
	    return user1;
	}

	@Override
	public User viewUser(Integer userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if(user != null ) {
			return user.get();
		}
		else {
			throw new UserException("Nie znaleziono użytkownika.");
		}
	}

	@Override
	public List<User> viewUserAll() throws UserException {
		List<User> userList = userRepository.findAll();
		if(userList.size() != 0) {
			return userList;
		}
		else {
			throw new UserException("Nie znaleziono użytkownika.");
		}
	}

	@Override
	public User updateUser(UserDTO user, Integer userId) throws UserException {

		User user1 = userRepository.findById(userId).
				orElseThrow(() -> new UserException("Użytkownik o podanym identyfikatorze nie istnieje."));
		user1.setUsername(user.getUsername());
		user1.setEmail(user.getEmail());
		user1.setMobileNumber(user.getMobileNumber());
		user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user1.setRole(user.getRole());

		return userRepository.save(user1);
	}

	@Override
	public String deleteUser(Integer userId) throws UserException {

		 Optional<User> user = userRepository.findById(userId);
		 if(user.isPresent()) {
			 List<Orders> orders = orderRepository.findAll();
			 for(int i=0;i<orders.size();i++) {
				 if(orders.get(i).getUser().getUserId() == userId) {
					 orderRepository.delete(orders.get(i));
				 }
			 }
			 userRepository.delete(user.get());
			 return "Konto zostało usunięte!";
		 }
		 throw new UserException("Nie znaleziono użytkownika.");
		
	}
	

}
