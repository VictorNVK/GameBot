package app.main.GameBot.models;

import app.main.GameBot.states.UserState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Long chatId;

    @Enumerated
    private UserState userState;

    @Column
    private String language;

}