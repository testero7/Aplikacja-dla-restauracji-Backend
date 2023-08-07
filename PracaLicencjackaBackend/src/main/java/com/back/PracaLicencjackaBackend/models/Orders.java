package com.back.PracaLicencjackaBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	private LocalDate orderDate;
	private String OrderStatus;
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "orderId")
	private List<ProductDtoSec> products = new ArrayList<>();
	@OneToOne
	private Address address;

}

