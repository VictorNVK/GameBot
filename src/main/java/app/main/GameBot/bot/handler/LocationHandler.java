package app.main.GameBot.bot.handler;

import app.main.GameBot.bot.keyboard.LocationKeyboard;
import app.main.GameBot.bot.messager.Messager;
import app.main.GameBot.bot.messager.MessagerEn;
import app.main.GameBot.bot.messager.MessagerRu;
import app.main.GameBot.location.LocationInit;
import app.main.GameBot.models.Item;
import app.main.GameBot.models.Player;
import app.main.GameBot.other.Logger;
import app.main.GameBot.repositories.ItemRepository;
import app.main.GameBot.repositories.PlayerRepository;
import app.main.GameBot.states.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
/*Класс управления для обновлений связанных с локациями, все методы вызываюся в главном классе бота, но есть и
вспомогательные*/
public class LocationHandler {

    private Messager messager;
    private final LocationKeyboard locationKeyboard;
    private final ItemRepository itemRepository;
    private final PlayerRepository playerRepository;
    private final Logger logger;
    private final LocationInit locationInit;


    private void choose_lang(String lang) {
        if (lang.startsWith("rus")) {
            messager = new MessagerRu();
        } else if (lang.startsWith("eng")) {
            messager = new MessagerEn();
        }
    }

    public SendMessage clearing_start_location(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setText(messager.getStart_location());
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    public SendMessage action_menu(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getLocation_menu());
        sendMessage.setReplyMarkup(locationKeyboard.action_menu_keyboard(lang));
        return sendMessage;
    }

    public SendMessage search(Long chatId, String lang, Player player){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Random random = new Random();
        int number = random.nextInt(2);
        if(player.getLocation().equals(Location.CLEARING)){
            if (number == 0) {
                Item item = searchRandomItem(locationInit.getClearing().getItems(),
                        player, locationInit.getClearing().getRooms());
                sendMessage = item(sendMessage, player, lang, item);
            }else {

            }
        }
        else if(player.getLocation().equals(Location.SUBURB)){
            if (number == 0) {
                Item item = searchRandomItem(locationInit.getSuburb().getItems(),
                        player, locationInit.getSuburb().getRooms());
                sendMessage = item(sendMessage, player, lang, item);
            }else {

            }
        }

        return sendMessage;
    }
    public SendMessage search_await(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getSearch_await());
        return sendMessage;
    }
    public SendMessage change_location(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getChooseLocationToChange());
        List<app.main.GameBot.location.Location> locations = locationInit.getLocations();
        sendMessage.setReplyMarkup(locationKeyboard.location_list_keyboard(locations, lang));
        return sendMessage;
    }
    public SendMessage location_has_been_chosen(Long chatId, String lang){
        choose_lang(lang);
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messager.getLocation_has_been_chosen());
        return sendMessage;
    }

    private Item searchRandomItem(ArrayList<Item> items, Player player, Integer rooms){
        var random = new Random();
        var randomRoom = random.nextInt(rooms) + 1;
        player.setRoom(randomRoom);
        playerRepository.save(player);

        var index = random.nextInt(items.size());
        var randomItem = items.get(index);
        randomItem.setPlayer(player);

        return randomItem;
        /*В дальнейшем здесь можно будет реализовать встречу игроков*/
    }
    private SendMessage item(SendMessage sendMessage, Player player, String lang, Item item){
        if(itemRepository.findItemByItemNameEnAndPlayer(item.getItemNameEn(), player) != null){
            Item item1 = itemRepository.findItemByItemNameEnAndPlayer(item.getItemNameEn(), player);
            item1.setCount(item1.getCount() + item.getCount());
            itemRepository.save(item1);
        }else {
            itemRepository.save(item);
        }
        if(lang.equals("rus")) {
            sendMessage.setText(messager.getYourItemFind() + item.getItemNameRu() + " " + item.getCount() + "x");
        }else {
            sendMessage.setText(messager.getYourItemFind() + item.getItemNameEn() + " " + item.getCount() + "x");
        }
        logger.log(player.getNickname(), player.getId(), "нашёл предмет", item.getItemNameRu() + " " + item.getCount());
        return sendMessage;
    }

}
