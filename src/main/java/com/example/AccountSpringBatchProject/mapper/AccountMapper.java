package com.example.AccountSpringBatchProject.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.example.AccountSpringBatchProject.model.Account;

public class AccountMapper implements FieldSetMapper<Account>{

	
	//Maps a FieldSet to an Account object
	@Override
	public Account mapFieldSet(FieldSet fieldSet) throws IndexOutOfBoundsException {
		Account account = new Account();
		account.setId(fieldSet.readInt(0));
		account.setName(fieldSet.readString(1));
		account.setAge(fieldSet.readInt(2));
		account.setAmount(fieldSet.readDouble(3));
		account.setPhone(fieldSet.readString(4));
		return account;
	}

}
