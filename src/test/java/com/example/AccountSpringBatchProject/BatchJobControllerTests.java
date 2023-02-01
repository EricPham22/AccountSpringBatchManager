package com.example.AccountSpringBatchProject;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.AccountSpringBatchProject.controller.BatchJobController;

@ExtendWith(MockitoExtension.class)
public class BatchJobControllerTests {
	
	@Mock
	JobLauncher jobLauncher;
	
	@Mock
	Job job;
	
	@Mock
	JobExecution jobExecution;
	
	@InjectMocks
	BatchJobController batchJobController;
	
	@Test
	void testRunBatchJob() throws Exception {
		ResponseEntity<?> output = batchJobController.runBatchJob();
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Nested
	class throwExceptions {
		
		@Test
		void jobIsRunning() throws Exception {
			when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenThrow(JobExecutionAlreadyRunningException.class);
			Throwable error = assertThrows(JobExecutionAlreadyRunningException.class, () -> {
					batchJobController.runBatchJob();
			});
			assertEquals(JobExecutionAlreadyRunningException.class, error.getClass());
		}
		
		@Test
		void jobIsNull() throws Exception {
			when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenThrow(IllegalArgumentException.class);
			Throwable error = assertThrows(IllegalArgumentException.class, () -> {
					batchJobController.runBatchJob();
			});
			assertEquals(IllegalArgumentException.class, error.getClass());
		}
	}
}
