package projeto.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.entidades.Usuario;
import projeto.infra.UserAlreadyExists;
import projeto.repositories.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    //Faz uma instancia de um objeto do tipo userRepository para as alterações na
    @Getter
    private Usuario usuarioLogado;

    public List<Usuario> getAllUsers(){
        //Lista todas as entidades do banco de dados
        return userRepository.findAll();
    }

    public Usuario register(Usuario usuario) {
        //Se o email do usuário passado no paramêtro já foi registrado
        //É lançado uma exceção
        if(jaExisteUsuario(usuario.getEmail())){
            throw new UserAlreadyExists();
        }
        //Caso contrário ele salva o novo usuário no banco de dados
        return userRepository.save(usuario);
    }

    public boolean jaExisteUsuario(String email){
        //Procura se já existe algum usuário com o mesmo email do paramêtro
        //Se sim ele retorna true
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean login(String email, String password){
        //Procura se existe um usuário com o email passado no paramêtro
        Optional<Usuario> user = userRepository.findByEmail(email);
        //compara a senha do usuário do banco de dados com a senha passada como paramêtro
        if(user.isPresent() && user.get().getPassword().equals(password)){
            //Define o usuárioLogado como o usuário no qual fez o login
            usuarioLogado = user.get();
            return true;
        }
        return false;
    }

    public void logOut(){
        usuarioLogado = null;
    }
}
