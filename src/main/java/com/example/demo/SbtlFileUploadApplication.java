package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbtlFileUploadApplication {

	public static void main(String[] args) {
		new File(MyController.uploadDirectory).mkdir();
		SpringApplication.run(SbtlFileUploadApplication.class, args);
	}

}
