package com.example.AccountSpringBatchProject;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.step.AccountProcessor;

public class AccountProcessorTests {

	@Test
	void canSetNameToUpperCase() {
		AccountProcessor processor = new AccountProcessor();
		Account account = new Account();
		account.setName("rico");
		account.setId(0);
		account.setAmount(5.00);
		String output = processor.process(account);
		assertTrue(output.contains("0"));
		assertTrue(output.contains("RICO"));
		assertTrue(output.contains("5.00"));
	}
	
	@Test
	void canSubtractTen() {
		AccountProcessor processor = new AccountProcessor();
		Account account = new Account();
		account.setId(0);
		account.setName("rico");
		account.setAmount(11.00);
		String output = processor.process(account);
		assertTrue(output.contains("1.00"));
		assertTrue(output.contains("RICO"));
	} 
}
