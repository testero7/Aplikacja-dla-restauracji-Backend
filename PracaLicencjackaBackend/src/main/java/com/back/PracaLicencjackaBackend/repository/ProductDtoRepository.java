package com.back.PracaLicencjackaBackend.repository;

import com.back.PracaLicencjackaBackend.models.ProductDTO;
import com.back.PracaLicencjackaBackend.models.ProductDtoSec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDtoRepository extends JpaRepository<ProductDtoSec, Integer>{
    List<ProductDtoSec> findProductDtoSecByProductId(Integer productId);
}
