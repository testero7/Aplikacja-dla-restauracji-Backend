package com.back.PracaLicencjackaBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int addressId;
	private String buildingNo;
	private String city;
	private String street;
	private String country;
	private String pincode;

}
