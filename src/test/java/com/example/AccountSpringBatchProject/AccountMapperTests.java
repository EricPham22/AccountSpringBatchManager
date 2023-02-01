package com.example.AccountSpringBatchProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.AccountSpringBatchProject.mapper.AccountMapper;
import com.example.AccountSpringBatchProject.model.Account;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTests {

	@Test
	public void testMapFieldSet() throws BindException {
		FieldSet mockFieldSet = mock(FieldSet.class);
		Account expectedAccount = new Account(1, "Rico", 22, 100000.34, "123-432-3432");
		AccountMapper accountMapper = new AccountMapper();
		when(mockFieldSet.readInt(0)).thenReturn(1);
		when(mockFieldSet.readString(1)).thenReturn("Rico");
		when(mockFieldSet.readInt(2)).thenReturn(22);
		when(mockFieldSet.readDouble(3)).thenReturn(100000.34);
		when(mockFieldSet.readString(4)).thenReturn("123-432-3432");
		Account output = accountMapper.mapFieldSet(mockFieldSet);
		assertEquals(output.getId(), expectedAccount.getId());
		assertEquals(output.getName(), expectedAccount.getName());
		assertEquals(output.getAge(), expectedAccount.getAge());
		assertEquals(output.getAmount(), expectedAccount.getAmount(), 0.01);
		assertEquals(output.getPhone(), expectedAccount.getPhone());
	}
	
	@Test
	public void testIndexOutOfBound() {
		FieldSet mockFieldSet = mock(FieldSet.class);
		AccountMapper accountMapper = new AccountMapper();
		when(mockFieldSet.readInt(0)).thenReturn(1);
		when(mockFieldSet.readString(1)).thenThrow(IndexOutOfBoundsException.class);
		Throwable error = assertThrows(IndexOutOfBoundsException.class, () -> {
			accountMapper.mapFieldSet(mockFieldSet);
		});
		assertEquals(IndexOutOfBoundsException.class, error.getClass());
	}
	
}
