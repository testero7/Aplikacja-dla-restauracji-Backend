package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.models.UserSecurity;
import com.back.PracaLicencjackaBackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByMobileNumber(username);
		if(user != null) {
			return new UserSecurity(user);
		}
		throw new UsernameNotFoundException("Nie istnieje taki użytkownik");
	}

}
