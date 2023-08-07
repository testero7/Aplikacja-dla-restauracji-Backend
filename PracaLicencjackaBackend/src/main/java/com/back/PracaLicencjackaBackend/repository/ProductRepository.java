package com.back.PracaLicencjackaBackend.repository;

import com.back.PracaLicencjackaBackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
