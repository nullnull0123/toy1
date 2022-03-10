package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

	@Test
	@DisplayName("slf4j test")
	public void slf4j_test(){
		log.info("logging done correctly");
	}

}
