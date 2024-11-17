package projeto.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Column(length = 800)
    private String conteudo;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario;
}
