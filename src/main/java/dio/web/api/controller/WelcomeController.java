package dio.web.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //-> Serviços http
public class WelcomeController {
    @GetMapping // -> recurso http do tipo get (Ao iniciar o app e abrir o localhost:8080 vai aparecer essa msg)
    public String welcome(){
        return "Welcome to my Spring boot web api";
    }
}
