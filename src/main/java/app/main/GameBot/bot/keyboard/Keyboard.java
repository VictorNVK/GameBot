package app.main.GameBot.bot.keyboard;

import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;

public abstract class Keyboard {

    public Messager messager;

    void choose_lang(String lang){
        if(lang.startsWith("rus")){
            messager = new MessagerRu();
        }else if (lang.startsWith("eng")){
            messager = new MessagerEn();
        }
    }

}
