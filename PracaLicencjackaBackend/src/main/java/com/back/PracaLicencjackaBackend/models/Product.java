package com.back.PracaLicencjackaBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String imagePath;
	private String productName;
	private Double price;
	private String specification;
	private Integer quantity;

	@ManyToOne(cascade = CascadeType.ALL)
	private Category category = new Category();
}
