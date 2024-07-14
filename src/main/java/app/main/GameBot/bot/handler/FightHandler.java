package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.FightKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.location.Location;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.Enemy;
import app.main.GameBot.models.Fight;
import app.main.GameBot.models.Player;
import app.main.GameBot.models.User;
import app.main.GameBot.repositories.EnemyRepository;
import app.main.GameBot.repositories.FightRepository;
import app.main.GameBot.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class FightHandler {

    private Messager messager;
    private final FightKeyboard fightKeyboard;
    private final LocationInit locationInit;
    private final FightRepository fightRepository;
    private final EnemyRepository enemyRepository;
    private final PlayerRepository playerRepository;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage under_attack(Long chatId, String lang, User user, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Location location = search_location(player.getLocation());
        Random random = new Random();
        var number = random.nextInt(location.getEnemy().size());

        if(lang.equals("rus")){
            var text = messager.getYou_under_attack() + location.getEnemy().get(number).getNameRu();
            text += "\n\n" + messager.getYou_have_20_second();
            sendMessage.setText(text);
        }else{
            var text = messager.getYou_under_attack() + location.getEnemy().get(number).getNameEn();
            text += "\n\n" + messager.getYou_have_20_second();
            sendMessage.setText(text);
        }
        app.main.GameBot.enemy.Enemy enemy =location.getEnemy().get(number);

        Enemy enemy_model = new Enemy();
        enemy_model.setAttack(enemy.getAttack());
        enemy_model.setNameEn(enemy.getNameEn());
        enemy_model.setEnergy(enemy.getEnergy());
        enemy_model.setHealth(enemy.getHealth());
        enemy_model.setDefense(enemy.getDefense());
        enemyRepository.save(enemy_model);

        Fight fight = new Fight();
        fight.setCounter(1);
        fight.setEnemy(enemy_model);
        fight.setPlayer(player);

        fightRepository.save(fight);
        return sendMessage;
    }

    private Location search_location(String name){
        for(Location location : locationInit.getLocations()){
            if(location.getNameEn().equals(name)){
                return location;
            }
        }
        return null;
    }
    public SendMessage enemy_attack(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Удар");
        Fight fight = fightRepository.findByPlayer(player);
        app.main.GameBot.enemy.Enemy enemy = search_enemy(fight.getEnemy().getNameEn(), player.getLocation());
        enemy.scale(player);
        Random random = new Random();
        var number = random.nextInt(2);
        if(number == 5){
            player = enemy.attack(player);
        }else {
            player = enemy.attack_talent(player, fight.getCounter());
        }
        enemyRepository.save(enemy.toModel(enemy, fight.getEnemy()));

        playerRepository.save(player);
        fight.setCounter(fight.getCounter() + 1);
        fightRepository.save(fight);
        return sendMessage;
    }

    private app.main.GameBot.enemy.Enemy search_enemy(String name, String location_name){
            Location location = search_location(location_name);
            for(app.main.GameBot.enemy.Enemy enemy : location.getEnemy()){
                if(enemy.getNameEn().equals(name)){
                    return enemy;
                }
            }
        return null;
    }
}
