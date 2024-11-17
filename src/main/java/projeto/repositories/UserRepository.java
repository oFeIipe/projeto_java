package projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projeto.entidades.Usuario;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}