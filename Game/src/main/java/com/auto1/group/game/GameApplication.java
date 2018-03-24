package com.auto1.group.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.auto1.group.game.service.GameHelper;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
/**
 * This class is Spring Boot Application for Playing
 * 
 * @author yelsa03
 *
 */
public class GameApplication implements CommandLineRunner {

	@Autowired
	private GameHelper gameHelper;

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		gameHelper.run();
	}

}
