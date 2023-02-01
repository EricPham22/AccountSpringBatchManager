package com.example.AccountSpringBatchProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.AccountSpringBatchProject.configuration.BatchConfiguration;
import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.step.AccountProcessor;
import com.example.AccountSpringBatchProject.step.AgeProcessor;

@ExtendWith(MockitoExtension.class)
public class BatchConfigurationTests {
	
	@Mock
	StepBuilderFactory stepBuilderFactory;
	
	@InjectMocks
	BatchConfiguration batchConfiguration;

	@BeforeEach
	void beforeEach() {
		batchConfiguration = new BatchConfiguration();
	}
	
	@Test
	@DisplayName("returns FlatFileIteamReader")
	void canReadTextFile() throws UnexpectedInputException, ParseException, Exception {
		Account expectedAccount = new Account(1, "Rico", 22, 10000.00, "137-090-2930");
		FlatFileItemReader<Account> output = batchConfiguration.textFileReader();
		output.open(new ExecutionContext());
		Account account = output.read();
		assertEquals(expectedAccount.getAge(), account.getAge());
		assertEquals(expectedAccount.getId(), account.getId());
		assertEquals(expectedAccount.getAmount(), account.getAmount(), 0.01);
		assertEquals(expectedAccount.getName(), account.getName());
		assertEquals(expectedAccount.getPhone(), account.getPhone());
	}
	
	@Test
	@DisplayName("returns new AgeProcessor")
	void returnsNewAgeProcessor() {
		AgeProcessor processor = batchConfiguration.ageProcessor();
		assertNotNull(processor);
	}
	
	@Test
	@DisplayName("returns writer")
	void canWrite() {
		RepositoryItemWriter<?> writer = batchConfiguration.writer();
		assertNotNull(writer);
	}
	
	@Test
	void returnNewListener() {
		JobExecutionListener jobExecutionListener = batchConfiguration.listener();
		assertNotNull(jobExecutionListener);
	}
	
	@Test
	void returnsAccountProcessor() {
		AccountProcessor output = batchConfiguration.accountProcessor();
		assertNotNull(output);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void returnsItemWriter() {
		FlatFileItemWriterBuilder<String> builder = mock(FlatFileItemWriterBuilder.class);
		when(builder.name(any(String.class))).thenReturn(builder);
		FlatFileItemWriter<?> output = batchConfiguration.itemWriter();
		assertNotNull(output);
		verify(builder, times(1)).name(any(String.class));
	}
	
	@Test
	void returnsItemReader() {
		RepositoryItemReader<Account> output = batchConfiguration.itemReader();
		assertNotNull(output);
	} 
	
	@Test
	void returnsStep1() throws Exception {
		StepBuilder builder = mock(StepBuilder.class);
		when(stepBuilderFactory.get("step1")).thenReturn(builder);
		Step output = batchConfiguration.step1();
		verify(stepBuilderFactory, times(1)).get("step1");
	}
	
}
