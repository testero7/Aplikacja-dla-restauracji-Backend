package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.exceptions.UserException;
import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.models.UserDTO;


import java.util.List;

public interface UserService {
	
 public User createUser(UserDTO user) throws UserException;

 public User updateUser(UserDTO user, Integer userId) throws UserException;

 public User viewUser(Integer userId) throws UserException;

 public List<User> viewUserAll() throws UserException;
 
 public String deleteUser(Integer userId) throws UserException;
}
