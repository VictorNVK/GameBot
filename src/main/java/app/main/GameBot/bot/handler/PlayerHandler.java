package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.PlayerKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.repositories.TalentRepository;
import app.main.GameBot.states.Location;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.way.Way;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
/*Класс управления для обновлений связанных с игроком, все методы вызываюся в главном классе бота, но есть и
вспомогательные*/
public class PlayerHandler {

    private Messager messager;
    private final PlayerKeyboard playerKeyboard;
    private final TalentRepository talentRepository;
    private final PlayerRepository playerRepository;

    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public Player create_player(String name, User user){
        var player = new Player();
        player.setId(user.getId());
        player.setNickname(name);
        player.setLevel(0);
        player.setHealth(20);
        player.setEnergy(10);
        player.setBlood(5);
        player.setAttack(1);
        player.setDefense(0);
        player.setBarrier(0);
        player.setHealthRegeneration(1);
        player.setEnergyRegeneration(1);
        player.setBloodRegeneration(1);
        player.setLocation(Location.CLEARING);
        player.setCrystals(50);
        return player;
    }

    public SendMessage character_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setText(messager.getCharacterMenu());
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(playerKeyboard.character_menu(lang));
        return sendMessage;
    }
    public SendMessage sendCharacteristics(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getCharacteristics() +
                "\n\n"
                + messager.getLevel() + player.getLevel() + "\uD83C\uDF1F"
                +"\n"+ messager.getHealth() + player.getHealth() + "♥\uFE0F"
        +"\n" +messager.getEnergy() + player.getEnergy() + "⚡\uFE0F"
        +"\n" + messager.getBlood() + player.getBlood() + "\uD83E\uDE78"
                +"\n" + messager.getAttack() + player.getAttack() + "\uD83D\uDDE1"
        +"\n" +messager.getDefense() + player.getDefense() + "\uD83D\uDEE1"
                        +"\n" +messager.getBarrier() + player.getBarrier() + "\uD83D\uDD35"
        /*+"\n" + messager.getHealthRegeneration() + player.getHealthRegeneration()
        +"\n" + messager.getEnergyRegeneration() + player.getEnergyRegeneration()
        +"\n" + messager.getBloodRegeneration() + player.getBloodRegeneration()*/
        );

        sendMessage.setReplyMarkup(playerKeyboard.characteristics_keyboard(lang));
        return sendMessage;
    }
    public SendMessage train_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(playerKeyboard.train_menu(lang));
        return sendMessage;
    }
    public SendMessage talents_list(Long chatId, String lang, List<Talent> talents, Way way, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(playerKeyboard.talents_list(talents, lang));
        player.setLastBranch(way.getNameEn());
        playerRepository.save(player);
        return sendMessage;
    }
    public SendMessage your_talent_stats(Talent talent, Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        app.main.GameBot.models.Talent talentModel = null;
        if(talentRepository.findTalentByPlayerAndName(player, talent.getNameEn()) == null) {
             talentModel = new app.main.GameBot.models.Talent();

        }else {
            talentModel = talentRepository.findTalentByPlayerAndName(player, talent.getNameEn());
        }
        talentModel.setPlayer(player);
        talentModel.setName(talent.getNameEn());
        talentModel.setLevel(talentModel.getLevel());

        talentRepository.save(talentModel);
        player.setLastTalent(talent.getNameEn());
        playerRepository.save(player);

        var text = messager.getYour_talent_stats() + "\n\n";
        if(lang.equals("rus")){
            text = text + talent.descriptionRu(talentModel);
        }else if(lang.equals("eng")){
            text = text + talent.descriptionEn(talentModel);
        }
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(playerKeyboard.up_talent_menu(lang, player));

        return sendMessage;
    }
    public SendMessage your_balance(Long chatId, String lang, Integer balance){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getYour_balance() + balance + "\uD83D\uDC8E" );
        return sendMessage;
    }
    public SendMessage talent_up(Long chatId, String lang, String talent_name, Player player, Talent _talent,
                                 List<Way> ways){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        var talent = talentRepository.findTalentByPlayerAndName(player, talent_name);

        if(talent.getLevel() >= 20){
            sendMessage.setText(messager.getTalent_is_maxed());
            return sendMessage;
        }
            var crystal_count = 25;
            for(int i = 0; i< talent.getLevel(); i++){
                crystal_count = crystal_count +25;
            }
            if(player.getCrystals() >= crystal_count) {
                talent.setLevel(talent.getLevel() + 1);
                talentRepository.save(talent);
                player.setCrystals(player.getCrystals() - crystal_count);
                if(lang.equals("rus")){
                    sendMessage.setText(_talent.descriptionRu(talent));
                }else if(lang.equals("eng")) {
                    sendMessage.setText(_talent.descriptionEn(talent));
                }
                var way = searchWay(talent.getName(), ways);
                if(way != null){
                    var way_name = way.getNameEn();
                    if(way_name.equals("Sword way")){
                        player.setHealth(player.getHealth() + 10);
                    }
                    /*Место для раскачки ветки*/
                }
                playerRepository.save(player);
            }else{
                sendMessage.setText(messager.getLittle_crystals());
            }
        return sendMessage;
    }
    public SendMessage ways_list(Long chatId, String lang, List<Way> ways){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChoose_param_of_menu());
        sendMessage.setReplyMarkup(playerKeyboard.waysList_list(ways, lang));
        return sendMessage;
    }

    private Way searchWay(String _talent, List<Way> ways){
        for(Way way : ways){
            for(Talent talent: way.getTalents()){
                if(talent.getNameEn().equals(_talent)){
                    return way;
                }
            }
        }
        return null;
    }
}
