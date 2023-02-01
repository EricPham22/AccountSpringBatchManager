package com.example.AccountSpringBatchProject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import com.example.AccountSpringBatchProject.listener.JobCompletionListener;
import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class JobCompletionListenerTests {

	@Mock
	AccountRepository accountRepo;

	@InjectMocks
	JobCompletionListener jobCompletionListener;
	
	@Test
	void printsAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(1, "Rico", 22, 23.21, "123-342-1423"));
		JobExecution jobExecution = mock(JobExecution.class);
		when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
		when(accountRepo.findAll()).thenReturn(accounts);
		jobCompletionListener.afterJob(jobExecution);
		verify(jobExecution, times(1)).getStatus();
		verify(accountRepo, times(1)).findAll();
	}
	
	@Test
	void logBeforeJob() {
		JobExecution jobExecution = mock(JobExecution.class);
		when(jobExecution.getStatus()).thenReturn(BatchStatus.STARTING);
		jobCompletionListener.afterJob(jobExecution);
		verify(jobExecution, times(1)).getStatus();
	}
}
