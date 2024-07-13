package app.main.GameBot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nameRu;

    @Column
    private String nameEn;

    @Column
    private Integer health;

    @Column
    private Integer energy;

    @Column
    private Integer attack;

    @Column
    private Integer defense;

}
