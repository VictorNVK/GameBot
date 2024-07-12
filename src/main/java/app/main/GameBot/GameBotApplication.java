package app.main.GameBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GameBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameBotApplication.class, args);
	}

}
