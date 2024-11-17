package projeto.entidades;

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
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

}
