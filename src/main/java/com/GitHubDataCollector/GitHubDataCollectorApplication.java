package com.GitHubDataCollector;

import com.GitHubDataCollector.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
public class GitHubDataCollectorApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GitHubDataCollectorApplication.class, args);
	}
}
