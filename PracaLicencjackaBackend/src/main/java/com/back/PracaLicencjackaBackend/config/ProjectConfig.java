package com.back.PracaLicencjackaBackend.config;

import com.back.PracaLicencjackaBackend.security.JwtAuthenticationFilter;
import com.back.PracaLicencjackaBackend.security.JwtauthEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ProjectConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Autowired
	private JwtauthEntry jwtauthEntry;
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	 }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		 http.cors();
		 http.csrf()
		 .disable()
		 .authorizeRequests()
		 .antMatchers("/login","/reviews/showAllReviews", "/User/create","/current-user", "/User/getAllProduct").permitAll()
         .antMatchers(
				 "/reviews/create",
				 "/User/cart/{userId}/{quantity}/{productId}",
				 "/User/getAllProductAddedInCart/{userId}",
				 "/User/removeProductFromCart/{productId}/{userId}",
				 "/User/updatingQuantity/{productId}/{quantity}/{userId}",
				 "/User/removeAllProductfromCart/{userId}",
				 "/order/orderProduct/{userId}",
				// "/User/cancelOrder/{orderId}",
				 "/order/getOrderById/{orderId}",
				// "/User/delete/{userId}",
				 "/User/getAllOrdersByUser/{userId}").hasAnyAuthority("USER","user","ADMIN","admin")
		.antMatchers("/admin/getAllOrders","/order/changeOrderStatus/{orderId}").hasAnyAuthority("ADMIN","admin","CHEF","chef","DELIVERY","delivery")
         .anyRequest().hasAnyAuthority("ADMIN","admin")
         .and()
		.exceptionHandling().authenticationEntryPoint(this.jwtauthEntry)
		.and()
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	 }


}
