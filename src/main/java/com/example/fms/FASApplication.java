package com.example.fms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class FASApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FASApplication.class, args);
	}

	public void run(String... args) throws Exception {
		try {
			Class.forName("org.h2.Driver");
			Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
			System.out.println("Connection successful");
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
