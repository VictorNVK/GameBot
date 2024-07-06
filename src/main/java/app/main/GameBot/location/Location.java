package app.main.GameBot.location;

import app.main.GameBot.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    private String nameRu;

    private String nameEn;

    private ArrayList<Item> items;

    private Integer rooms;
}
