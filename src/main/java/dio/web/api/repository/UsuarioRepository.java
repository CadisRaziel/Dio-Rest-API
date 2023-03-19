package dio.web.api.repository;

import dio.web.api.handler.BusinessException;
import dio.web.api.handler.CampoObrigatorioException;
import dio.web.api.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    public void save(Usuario usuario) {
        //montando um cenario para nossas exeções
        //se o usuario nao recebeu o atributo nome, eu quero gera uma exeção dizendo que o nome é obrigatorio
        if(usuario.getLogin() == null){
            throw new BusinessException("O campo login é obrigatório");
        }

        if(usuario.getPassword() == null){
            throw new CampoObrigatorioException("O campo password é obrigatório");
        }


        if(usuario.getId() == null) {
            System.out.println("Save - Recebendo o usuario na camada de repositorio");
        } else {
            System.out.println("Update - Recebendo o usuario na camada do repositorio");
        }
    }

    public void deleteById(Integer id) {
        System.out.println(String.format("Delete/id - Recebendo o id: %d para excluir um usuario", id));
        System.out.println(id);
    }

    public List<Usuario> findAll(){
        System.out.println("List - Listando os usuarios do sistema");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Vitu", "password"));
        usuarios.add(new Usuario("Ari", "masterpass"));
        return usuarios;
    }

    public Usuario findById(Integer id) {
        System.out.println(String.format("Find/id - Recebendo o id: %d para localizar um usuario", id));
        return new Usuario("vitutu", "password");
    }

    public Usuario findByUsername(String username) {
        System.out.println(String.format("Find/username - Recebendo o username: %s para localizar um usuario", username));
        return new Usuario("Vitu", "password");
    }
}
