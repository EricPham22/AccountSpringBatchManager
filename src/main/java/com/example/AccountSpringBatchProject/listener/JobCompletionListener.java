package com.example.AccountSpringBatchProject.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.AccountSpringBatchProject.model.Account;
import com.example.AccountSpringBatchProject.repository.AccountRepository;
import com.example.AccountSpringBatchProject.step.AgeProcessor;

@Component
public class JobCompletionListener implements JobExecutionListener{
	
	@Autowired
	AccountRepository accountRepo;
	
	private final Logger log = LoggerFactory.getLogger(AgeProcessor.class);
	
	//Logs after the job completes and prints out all accounts
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("BATCH JOB COMPLETED SUCCESSFULLY");
			for(Account account: accountRepo.findAll()) {
				log.info(account.toString());
			}
		}
	}

	//Logs when the job is starting
	@Override
	public void beforeJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.STARTING) {
			log.info("BATCH JOB STARTING");
		}		
	}
}
