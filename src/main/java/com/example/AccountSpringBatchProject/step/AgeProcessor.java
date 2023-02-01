package com.example.AccountSpringBatchProject.step;

import org.springframework.batch.item.ItemProcessor;

import com.example.AccountSpringBatchProject.model.Account;

public class AgeProcessor implements ItemProcessor<Account, Account>{
	

	//Processes the account and returns the account if the age is greater than 10 otherwise return null
	@Override
	public Account process(Account item) throws Exception {
		boolean isAgeGreaterThanTen = item.getAge() > 10; 
		return isAgeGreaterThanTen ? item : null;
	}
}
