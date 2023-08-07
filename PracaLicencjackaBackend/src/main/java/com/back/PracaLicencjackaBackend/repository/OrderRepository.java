package com.back.PracaLicencjackaBackend.repository;

import com.back.PracaLicencjackaBackend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

}
