package com.twitter.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import com.twitter.entity.Employee;
import com.twitter.repo.EmployeeRepo;

@Configuration
public class BatchConfiguration {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Bean
	public FlatFileItemReader<Employee> reader(){
		FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
		flatFileItemReader.setName("customers.csv");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	 private LineMapper<Employee> lineMapper() {
	        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(",");
	        lineTokenizer.setStrict(false);
	        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

	        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(Employee.class);

	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;

	    }
	 @Bean
	 public EmployeeProcessor processor() {
		 return new EmployeeProcessor();
	 }
	 
	 @Bean
	    public RepositoryItemWriter<Employee> writer() {
	        RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
	        writer.setRepository(employeeRepo);
	        writer.setMethodName("save");
	        return writer;
	    }
	 
	 @Bean
	    public Step step1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
	        return new StepBuilder("csv-step",jobRepository).<Employee, Employee>chunk(10,transactionManager)
	                .reader(reader())
	                .processor(processor())
	                .writer(writer())
	                .taskExecutor(taskExecutor())
	                .build();
	    }
	 
	 @Bean
	 
	 public Job runJob(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
	        return new JobBuilder("importCustomers",jobRepository)
	                .flow(step1(jobRepository,transactionManager)).end().build();

	    }
	 
	 @Bean
	    public TaskExecutor taskExecutor() {
	         SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
	        asyncTaskExecutor.setConcurrencyLimit(10);
	        return asyncTaskExecutor;
	    }
	
}
