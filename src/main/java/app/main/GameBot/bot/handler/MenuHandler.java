package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.MenuKeyboard;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class MenuHandler {

    private final MessagerRu messagerRu;
    private final MessagerEn messagerEn;
    private final MenuKeyboard menuKeyboard;

    public SendMessage start(Long chatId, String lang){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Select a language to interact with the bot/Выберите язык для взаимодействия с ботом");
        sendMessage.setReplyMarkup(menuKeyboard.select_lang());
        return sendMessage;
    }
}
