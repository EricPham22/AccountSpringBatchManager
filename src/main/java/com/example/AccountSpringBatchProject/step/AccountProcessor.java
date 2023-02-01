package com.example.AccountSpringBatchProject.step;

import org.springframework.batch.item.ItemProcessor;

import com.example.AccountSpringBatchProject.model.Account;

public class AccountProcessor implements ItemProcessor<Account, String>{

	//Processes the Account and returns a String with the id, name and amount
	@Override
	public String process(Account item) {
		int id = item.getId();
		String name = item.getName().toUpperCase();
		double amount = (item.getAmount() > 10) ? item.getAmount() : item.getAmount() - 10; 
		return String.format("%d, %s, %.2f", id, name, amount); 
	}

}