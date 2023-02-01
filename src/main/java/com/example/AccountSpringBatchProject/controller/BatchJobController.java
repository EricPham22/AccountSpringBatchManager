package com.example.AccountSpringBatchProject.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchJobController {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	//GET Endpoint to trigger a Spring Batch Job
	@GetMapping("/start")
	public ResponseEntity<?> runBatchJob() throws Exception {
		JobParameters parameters = new JobParametersBuilder()
		        .addLong("startAt", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(job, parameters);
		return new ResponseEntity<>(HttpStatus.OK); 
	}
}
