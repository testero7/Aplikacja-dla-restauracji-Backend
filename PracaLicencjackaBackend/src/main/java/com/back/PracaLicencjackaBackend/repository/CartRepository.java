package com.back.PracaLicencjackaBackend.repository;

import com.back.PracaLicencjackaBackend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
