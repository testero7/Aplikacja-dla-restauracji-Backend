package com.back.PracaLicencjackaBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int catId;
	private String categoryName;		
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Product> products = new ArrayList<>();

}
