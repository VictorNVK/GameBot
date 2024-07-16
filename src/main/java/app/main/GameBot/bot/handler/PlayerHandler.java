package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.PlayerKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.UpgradeProgress;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.*;
import app.main.GameBot.states.UserState;
import app.main.GameBot.talent.Talent;
import app.main.GameBot.way.Way;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
/*Класс управления для обновлений связанных с игроком, все методы вызываюся в главном классе бота, но есть и
вспомогательные*/
public class PlayerHandler {

    private Messager messager;
    private final PlayerKeyboard playerKeyboard;
    private final TalentRepository talentRepository;
    private final PlayerRepository playerRepository;
    private final UpgradeProgressRepository upgradeProgressRepository;
    private final UserRepository userRepository;
    private final WayRepository wayRepository;

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
        player.setLocation("Clearing");
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
                +"\n"+ messager.getHealth() + player.getHealthNow() + "/" + player.getHealth() + "♥\uFE0F"
        +"\n" +messager.getEnergy() + player.getEnergyNow() + "/" + player.getEnergy() + "⚡\uFE0F"
        +"\n" + messager.getBlood() + player.getBloodNow() + "/" + player.getBlood() + "\uD83E\uDE78"
                +"\n" + messager.getAttack() + player.getAttack() + "\uD83D\uDDE1"
        +"\n" +messager.getDefense() + player.getDefense() + "\uD83D\uDEE1"
                        +"\n" +messager.getBarrier() + player.getBarrier() + "\uD83D\uDD35"
        /*+"\n" + messager.getHealthRegeneration() + player.getHealthRegeneration()
        +"\n" + messager.getEnergyRegeneration() + player.getEnergyRegeneration()
        +"\n" + messager.getBloodRegeneration() + player.getBloodRegeneration()*/
        );

        sendMessage.setReplyMarkup(playerKeyboard.back_keyboard(lang));
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

        if(wayRepository.findWayByPlayerAndName(player, way.getNameEn()) != null) {
            var wayModel = wayRepository.findWayByPlayerAndName(player, way.getNameEn());
            player.setLastBranch(way.getNameEn());
            playerRepository.save(player);
            sendMessage.setReplyMarkup(playerKeyboard.talents_list(talents, lang, wayModel));

        }else {
            var way1 = new app.main.GameBot.models.Way();
            way1.setName(way.getNameEn());
            way1.setLevel(0);
            way1.setPlayer(player);
            wayRepository.save(way1);
            sendMessage.setReplyMarkup(playerKeyboard.talents_list(talents, lang, way1));
        }

        sendMessage.setText(messager.getChoose_param_of_menu());


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
        talentModel.setCounter(talent.getActive_time());

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
                talent.setLevel(talent.getLevel() + 1);
                talentRepository.save(talent);
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
                        player.setHealthNow(player.getHealthNow() + 10);

                    }else if(way_name.equals("Magus way")){
                        player.setEnergy(player.getEnergy() + 10);
                        player.setEnergyNow(player.getEnergyNow() +10);
                    }
                    else if(way_name.equals("Word way")){
                        player.setEnergy(player.getEnergy() + 5);
                        player.setBarrier(player.getBarrier() +3);
                    }
                    playerRepository.save(player);
                    /*Место для раскачки ветки*/
                }
        return sendMessage;
    }
    public SendMessage branch_up(Long chatId, String lang, app.main.GameBot.models.Way way){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        way.setLevel(way.getLevel() +1);
        wayRepository.save(way);
        sendMessage.setText(messager.getBranch_is_up() + way.getLevel());
        return sendMessage;
    }

    public SendMessage talent_up_info(Long chatId, String lang, Player player, String talent_name, User user){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        var talent = talentRepository.findTalentByPlayerAndName(player, talent_name);

        if(talent.getLevel() >= 20){
            sendMessage.setText(messager.getTalent_is_maxed());
            return sendMessage;
        }
        var crystal_count = 15;
        for(int i = 0; i< talent.getLevel(); i++){
            crystal_count = crystal_count +15;
        }
        var time = crystal_count * 5;

        if(player.getCrystals() >= crystal_count) {
            player.setCrystals(player.getCrystals() - crystal_count);

            UpgradeProgress upgradeProgress = new UpgradeProgress();
            upgradeProgress.setTalent(talent);

            Date date = new Date();
            var newDate = date.getTime() + TimeUnit.SECONDS.toMillis(time);
            var up_time = new Date(newDate);
            upgradeProgress.setTime(up_time);
            upgradeProgress.setPrice(crystal_count);
            upgradeProgress.setPlayer(player);
            upgradeProgressRepository.save(upgradeProgress);
            sendMessage.setText(messager.getUp_time() + time + " " + messager.getSecond() + " , " + messager.getAwait_up());
            playerRepository.save(player);
            sendMessage.setReplyMarkup(playerKeyboard.back_keyboard(lang));
            user.setUserState(UserState.GRADE);
            userRepository.save(user);
        }else {

            sendMessage.setText(messager.getLittle_crystals());
        }
        return sendMessage;
    }
    public SendMessage branch_up_info(Long chatId, String lang, Player player, String branch_name, User user){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        var branch = wayRepository.findWayByPlayerAndName(player, branch_name);

        if(branch.getLevel() >= 20){
            sendMessage.setText(messager.getBranch_is_maxed());
            return sendMessage;
        }
        if(branch.getLevel() == player.getLevel()){
            sendMessage.setText(messager.getLevel_is_maxed_for_branch());
            return sendMessage;
        }
        var crystal_count = 25;
        for(int i = 0; i< branch.getLevel(); i++){
            crystal_count = (int) Math.ceil(crystal_count * 1.5);
        }
        var time = crystal_count * 5;

        if(player.getCrystals() >= crystal_count) {
            player.setCrystals(player.getCrystals() - crystal_count);

            UpgradeProgress upgradeProgress = new UpgradeProgress();
            upgradeProgress.setWay(branch);

            Date date = new Date();
            var newDate = date.getTime() + TimeUnit.SECONDS.toMillis(time);
            var up_time = new Date(newDate);
            upgradeProgress.setTime(up_time);
            upgradeProgress.setPrice(crystal_count);
            upgradeProgress.setPlayer(player);
            upgradeProgressRepository.save(upgradeProgress);
            sendMessage.setText(messager.getUp_time_branch() + time + " " + messager.getSecond() + " , " + messager.getAwait_up());
            playerRepository.save(player);
            sendMessage.setReplyMarkup(playerKeyboard.back_keyboard(lang));
            user.setUserState(UserState.GRADE);
            userRepository.save(user);
        }else {
            sendMessage.setText(messager.getLittle_crystals());
        }
        return sendMessage;
    }


    @Transactional
    public SendMessage back_up(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        UpgradeProgress upgradeProgress = upgradeProgressRepository.findUpgradeProgressByPlayer(player);
        var price = upgradeProgress.getPrice();
        player.setCrystals(player.getCrystals() + price);
        playerRepository.save(player);
        upgradeProgressRepository.delete(upgradeProgress);
        sendMessage.setText(messager.getUp_is_delete());
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
