package app.main.GameBot.other;

import org.springframework.stereotype.Component;

@Component
public class Logger {

    public void log(String username, Integer playerId, String action, String option){
        System.out.println("игрок " + username + " (ID:" + playerId + ") сделал " +
                action + ", выбрал " + option);
    }
}
