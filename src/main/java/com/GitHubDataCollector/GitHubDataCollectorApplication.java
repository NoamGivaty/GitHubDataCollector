package com.GitHubDataCollector;

import com.GitHubDataCollector.service.AutoUpdateService;
import com.GitHubDataCollector.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class GitHubDataCollectorApplication {

	@Autowired
	AutoUpdateService autoUpdateService;

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(GitHubDataCollectorApplication.class, args);
	}

	@PostConstruct
	public void init() throws IOException, InterruptedException {
		autoUpdateService.startAutoUpdate();
	}
}
