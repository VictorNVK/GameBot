package app.main.GameBot.bot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationKeyboard extends Keyboard {

    public InlineKeyboardMarkup action_menu_keyboard(String lang){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        var craftButton = new InlineKeyboardButton();
        craftButton.setText(messager.getCraft());
        craftButton.setCallbackData("craft");
        row1.add(craftButton);

        var searchButton = new InlineKeyboardButton();
        searchButton.setText(messager.getLocationResearch());
        searchButton.setCallbackData("search");
        row1.add(searchButton);
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        var back = new InlineKeyboardButton();
        back.setText(messager.getBack());
        back.setCallbackData("back");
        row2.add(back);
        rows.add(row2);
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

}
