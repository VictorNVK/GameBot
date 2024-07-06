package app.main.GameBot.bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerKeyboard extends Keyboard {

    public InlineKeyboardMarkup character_menu(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var button1 = new InlineKeyboardButton();
        button1.setText(messager.getCharacteristics());
        button1.setCallbackData("characteristics");
        row1.add(button1);
        var button2 = new InlineKeyboardButton();
        button2.setText(messager.getTalents());
        button2.setCallbackData("talents");
        row1.add(button2);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup characteristics_keyboard(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row1.add(back);
        rows.add(row1);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

}
