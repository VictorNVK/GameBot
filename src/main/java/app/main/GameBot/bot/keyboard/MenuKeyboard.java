package app.main.GameBot.bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuKeyboard {

    public InlineKeyboardMarkup select_lang(){
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText("eng\uD83C\uDDEC\uD83C\uDDE7");
        button1.setCallbackData("lang-eng");
        var button2 = new InlineKeyboardButton();
        button2.setText("рус\uD83C\uDDF7\uD83C\uDDFA");
        button2.setCallbackData("lang-rus");
        row1.add(button1);
        row1.add(button2);
        rows.add(row1);

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
}
