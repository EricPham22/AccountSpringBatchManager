package com.example.AccountSpringBatchProject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.step.AgeProcessor;

public class AgeProcesserTests {

	@Test
	void testProcessReturnsAccount() throws Exception {
		AgeProcessor ageProcessor = new AgeProcessor();
		Account output = ageProcessor.process(new Account(1, "Rico", 22, 10000.32, "758-439-4298"));
		assertThat(output.getAge()).isGreaterThan(10);
	}
	
	@Test 
	void testProcessReturnsNull() throws Exception {
		AgeProcessor ageProcessor = new AgeProcessor();
		Account output = ageProcessor.process(new Account(1, "Rico", 2, 143.34, "543-342-5233"));
		assertThat(output).isNull();
	}
}
