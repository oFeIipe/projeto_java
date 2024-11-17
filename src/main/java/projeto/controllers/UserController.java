package projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.dto.LoginDTO;
import projeto.entidades.Usuario;
import projeto.services.UserService;


import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity <List<Usuario>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioAtual() {
        Usuario usuarioLogado = userService.getUsuarioLogado();
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(usuarioLogado);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario user) {
        Usuario novoUsuario = userService.register(user);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO user){
        if(userService.login(user.email(), user.password())){
            return ResponseEntity.ok("Login feito com sucesso");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(){
        userService.logOut();
        return ResponseEntity.ok("Logout feito com sucesso");
    }
}
