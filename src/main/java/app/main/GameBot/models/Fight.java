package app.main.GameBot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "player_id")
    @ManyToOne
    private Player player;

    @JoinColumn(name = "enemy_id")
    @ManyToOne
    private Enemy enemy;

    @Column
    private Integer counter;


}
