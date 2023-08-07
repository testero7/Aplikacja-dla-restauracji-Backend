package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.models.LoginForm;
import com.back.PracaLicencjackaBackend.models.User;
import com.back.PracaLicencjackaBackend.repository.UserRepository;
import com.back.PracaLicencjackaBackend.security.JwtAuthResponse;
import com.back.PracaLicencjackaBackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
	
	@Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenHelper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody LoginForm request) throws Exception {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
        String token=this.jwtTokenHelper.generateTokens(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }
    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException bce){
            throw new Exception("Wprowadzono błędne dane!");
        }
    }
    @PostMapping("/current-user")
	public User getCurrentEmployee(@RequestBody JwtAuthResponse token) {
		String username = jwtTokenHelper.getUsernameFromToken(token.getToken());
		return userRepository.findByMobileNumber(username);
	}

}
