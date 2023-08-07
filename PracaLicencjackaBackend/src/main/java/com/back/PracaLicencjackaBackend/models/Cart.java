package com.back.PracaLicencjackaBackend.models;

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
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductDtoSec> Cartproducts = new ArrayList<>();

}
