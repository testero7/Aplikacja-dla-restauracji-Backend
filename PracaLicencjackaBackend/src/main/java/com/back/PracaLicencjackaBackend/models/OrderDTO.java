package com.back.PracaLicencjackaBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int orderId;
    private LocalDate orderDate;
    private String OrderStatus;
    private User user;
    private List<ProductDtoSec> products = new ArrayList<>();
    private Address address;
}
