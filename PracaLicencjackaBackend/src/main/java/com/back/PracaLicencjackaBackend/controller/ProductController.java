package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.exceptions.ProductException;
import com.back.PracaLicencjackaBackend.models.Product;
import com.back.PracaLicencjackaBackend.models.ProductDTO;
import com.back.PracaLicencjackaBackend.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/create")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) throws ProductException {
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		Product product = productService.createProduct(productDTO);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PutMapping("/updateProduct/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) throws ProductException {
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		Product product = productService.updateProduct(productDTO, productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

}
