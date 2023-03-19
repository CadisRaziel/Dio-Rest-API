package dio.web.api.controller;

import dio.web.api.model.Usuario;
import dio.web.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") //-> estou dizendo que todos as requisiçoes http aqui vao iniciar com '/users'
//com isso eu posso remover todos /users de dentro dos getmapping/postmapping etc...
public class UsuarioController {

    //Injetando o UsuarioRepository (injetando dependencia)
    @Autowired
    private UsuarioRepository repository;

    @GetMapping() //não podemos ter dois @GetMapping(), devemos diferencia-los colocando um parametro
    public List<Usuario> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/{username}") //passando o parametro da função para a ulr
    //@PathVariable("username") -> se eu for passa uma variavel como parametro na url
    //eu preciso especificar com pathVariable e qual variavel que é
    public Usuario getOne(@PathVariable("username") String username) {
        return repository.findByUsername(username);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }

    @PostMapping()
    //@RequestBody -> o sprint vai interpreta que é pra eu pega o json e converter no objeto conforme nosso contrato
    public void postUser(@RequestBody Usuario usuario) {
        repository.save(usuario);
    }

    @PutMapping()
    public void putUser(@RequestBody Usuario usuario) {
        repository.save(usuario);
    }
}
