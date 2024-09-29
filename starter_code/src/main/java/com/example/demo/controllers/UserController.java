package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	private final PasswordEncoder passwordEncoder;

	public UserController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest, BindingResult result) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		if (result.hasErrors()) {
			logger.error("Errors: {}", result.getAllErrors());
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		logger.info("Received user creation request: {}", createUserRequest);
		if (createUserRequest.getPassword() == null) {
			return ResponseEntity.badRequest().build();
		}
		if (createUserRequest.getPassword().length() < 8) {
			logger.error("Failed to create user: password too short");
			return ResponseEntity.badRequest().build();
		}

		if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			logger.error("Failed to create user: password mismatch");
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);

		logger.info("User created successfully: " + user.getUsername());
		return ResponseEntity.ok(user);
	}
}
