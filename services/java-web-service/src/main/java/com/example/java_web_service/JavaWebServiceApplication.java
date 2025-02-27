package com.example.java_web_service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
public class JavaWebServiceApplication {

	String fileDir = "/app/data";
	String logFileName = "app.log";

	FileWriter fileWriter;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(new File(fileDir).toPath());
			fileWriter = new FileWriter(fileDir + "/" + logFileName, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/hello")
	public String hello() {
		try {
			fileWriter.write("log line..\n");
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Hello World! ";
	}

	public static void main(String[] args) {
		SpringApplication.run(JavaWebServiceApplication.class, args);
	}

}
