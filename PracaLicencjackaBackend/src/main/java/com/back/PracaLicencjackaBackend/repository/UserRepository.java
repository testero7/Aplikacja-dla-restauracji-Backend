package com.back.PracaLicencjackaBackend.repository;

import com.back.PracaLicencjackaBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByMobileNumber(String mobileNumber);
}
