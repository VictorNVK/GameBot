package app.main.GameBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GameBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameBotApplication.class, args);
	}

}
