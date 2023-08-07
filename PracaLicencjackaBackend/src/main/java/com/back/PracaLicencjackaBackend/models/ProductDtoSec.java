package com.back.PracaLicencjackaBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoSec {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	private Integer productId;
	private String imagePath;
	private String productName;
	private Double price;
	private Integer quantity;
}
