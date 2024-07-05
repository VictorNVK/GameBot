package app.main.GameBot.bot.messager;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MessagerRu extends Messager {
    private final String get_name = "Введите имя игрока:";
    private final String get_tg_name = "Использовать имя из telegram";
    private final String greeting = "Добро пожаловать ";
}
