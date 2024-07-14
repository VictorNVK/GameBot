package app.main.GameBot.bot.keyboard;

import app.main.GameBot.models.Player;
import app.main.GameBot.repositories.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FightKeyboard extends Keyboard{

    private final TalentRepository talentRepository;


    public InlineKeyboardMarkup action_menu(Lang chatId, String lang, Player player){
        choose_lang(lang);
        var keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();


        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }



}
