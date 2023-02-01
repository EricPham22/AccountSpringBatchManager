package com.example.AccountSpringBatchProject.configuration;

import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.example.AccountSpringBatchProject.listener.JobCompletionListener;
import com.example.AccountSpringBatchProject.mapper.AccountMapper;
import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.repository.AccountRepository;
import com.example.AccountSpringBatchProject.step.AccountProcessor;
import com.example.AccountSpringBatchProject.step.AgeProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
		
	//Reads a .txt file and maps value to an Account object
	@Bean
	public FlatFileItemReader<Account> textFileReader() throws Exception {
		FlatFileItemReader<Account> accountReader = new FlatFileItemReader<>();
		accountReader.setName("accountReader");
		accountReader.setResource(new ClassPathResource("test.txt"));
		DefaultLineMapper<Account> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		lineMapper.setFieldSetMapper(new AccountMapper());
		accountReader.setLineMapper(lineMapper);
		return accountReader;
	}

	//A processor that checks if age is > than 10
	@Bean
	public AgeProcessor ageProcessor() {
		return new AgeProcessor();
	}

	//Writes Account objects to a database via AccountRepository
	@Bean
	public RepositoryItemWriter<Account> writer() {
		RepositoryItemWriter<Account> writer = new RepositoryItemWriter<>();
		writer.setRepository(accountRepository);
		writer.setMethodName("save");
		return writer;
	}

	//A batch job that will run two steps sequentially 
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer()).listener(listener()).start(step1()).next(step2()).build();
	}

	//A step that with a reader from textFileReader, processor from ageProcessor and writer from writer()
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1").<Account, Account>chunk(5).reader(textFileReader()).processor(ageProcessor()).writer(writer()).build();
	}

	//A listener for a completed job
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

	//A reader that will read from a db from AccountRepository
	@Bean
	public RepositoryItemReader<Account> itemReader() {
		HashMap<String, Sort.Direction> sort = new HashMap<>();
		sort.put("id", Direction.ASC);
		RepositoryItemReader<Account> reader = new RepositoryItemReader<>();
		reader.setSort(sort);
		reader.setRepository(accountRepository);
		reader.setMethodName("findAll");
		return reader;
	}

	//A processor changes name and amount 
	@Bean
	public AccountProcessor accountProcessor() { 
		return new AccountProcessor();
	}

	//A writer that writes out to a .txt file
	@Bean
	public FlatFileItemWriter<String> itemWriter() {
		return new FlatFileItemWriterBuilder<String>().name("itemWriter")
				.resource(new FileSystemResource("target/test-outputs/output.txt"))
				.lineAggregator(new PassThroughLineAggregator<>()).build();
	}
	
	//A step that with a reader from itemReader, processor from accountProcessor and writer from itemWriter()
	@Bean 
	public Step step2() throws Exception {
		return stepBuilderFactory.get("step2").<Account, String>chunk(5)
				.reader(itemReader())
				.processor(accountProcessor())
				.writer(itemWriter())
				.build();
	}
}
