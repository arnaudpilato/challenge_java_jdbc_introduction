package com.wildcodeschool.challengeJDBCIntroduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeJdbcIntroductionApplication {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/challenge_jdbc_introduction?serverTimezone=GMT";
	private final static String DB_USER = "h4rryp0tt3r";
	private final static String DB_PASSWORD = "Horcrux4life!";

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJdbcIntroductionApplication.class, args);
	}

}
