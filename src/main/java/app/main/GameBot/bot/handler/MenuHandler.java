package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.MenuKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
@RequiredArgsConstructor
public class MenuHandler {

    private Messager messager;
    private final MenuKeyboard menuKeyboard;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage start(Long chatId, String lang) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Select a language to interact with the bot/Выберите язык для взаимодействия с ботом");
        sendMessage.setReplyMarkup(menuKeyboard.select_lang());
        return sendMessage;
    }

    public SendMessage get_name(Long chatId, String lang) {
        choose_lang(lang);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getGet_name());
        sendMessage.setReplyMarkup(menuKeyboard.use_tg_name(messager.getGet_tg_name()));
        return sendMessage;
    }

    public SendMessage greeting(Long chatId, String lang, String name) {
        choose_lang(lang);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getGreeting() + name);
        return sendMessage;
    }

    public SendMessage menu(Long chatId, String lang) {
        choose_lang(lang);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getMenu());
        sendMessage.setReplyMarkup(menuKeyboard.menu(lang));
        return sendMessage;
    }

    public SendMessage in_dev(Long chatId, String lang){
        choose_lang(lang);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getInDev());
        return sendMessage;
    }
}
