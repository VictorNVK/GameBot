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
public class Talent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private Integer level = 0;

    @Column
    private Boolean active = false;

    @Column
    private Integer counter;
}
