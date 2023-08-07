package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.exceptions.OrderException;
import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.AddressDTO;
import com.back.PracaLicencjackaBackend.models.OrderDTO;
import com.back.PracaLicencjackaBackend.models.Orders;
import java.util.List;


public interface OrderService {
    public Orders OrderProducts(Integer userId, AddressDTO Address) throws OrderException, UserException;

    public String cancelOrder(Integer orderId ) throws OrderException, UserException;

    public Orders getOrderById(Integer orderId) throws OrderException, UserException;

    public List<Orders> getAllOrdersByUser(Integer userId) throws OrderException, UserException;

    public List<Orders> getAllOrders() throws OrderException, UserException;

}
