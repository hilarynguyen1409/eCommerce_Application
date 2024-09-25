package com.example.demo;

import org.junit.jupiter.api.Test; // Import JUnit 5 Test annotation
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class) // Use JUnit 5's ExtendWith instead of RunWith
@SpringBootTest
public class SareetaApplicationTests {

	@Test
	public void contextLoads() {
		// This test will just check if the Spring context loads successfully.
	}
}

