## Aqui está um exemplo de código avançado em Spring Boot que cria uma tela completa de login, utiliza a anotação @SessionScope para gerenciar a sessão do usuário e utiliza o Hibernate para buscar o usuário no banco de dados:

### Crie uma entidade User que representa os dados do usuário:

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String username;
private String password;

    // Getters and setters
}
```

### Crie um repositório UserRepository que estende a interface JpaRepository do Spring Data JPA:

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

### Crie um componente UserSession para gerenciar a sessão do usuário usando a anotação @SessionScope:

```java
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UserSession {
private User user;

    // Getters and setters
}
```

### Crie um serviço UserService para lidar com a lógica de autenticação e autorização do usuário:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
private UserRepository userRepository;
private UserSession userSession;

    @Autowired
    public UserService(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            userSession.setUser(user);
            return true;
        }
        return false;
    }

    public boolean isAuthenticated() {
        return userSession.getUser() != null;
    }
}
```

### Crie um controlador LoginController para lidar com as requisições de login:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.authenticate(username, password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/home")
    public String home() {
        if (userService.isAuthenticated()) {
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}
```

### Crie uma página login.html para a tela de login:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <form action="/login" method="post">
        <input type="text" name="username" placeholder="Username" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <input type="submit" value="Login">
    </form>
    
    <p th:text="${error}" th:if="${error}"></p>
</body>
</html>
```

### Crie uma página home.html para a página inicial após o login:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>Welcome, <span th:text="${userSession.user.username}"></span>!</h1>

    <a href="/logout">Logout</a>
</body>
</html>
```

Certifique-se de ter as dependências necessárias do Spring Boot e do Hibernate configuradas corretamente no seu projeto.

Esse exemplo demonstra como criar uma tela de login completa usando as anotações @SessionScope, @Component e o Hibernate para buscar os dados do usuário no banco de dados. O UserSession é um componente de escopo de sessão que armazena o usuário autenticado durante a sessão. O UserService é responsável pela autenticação do usuário e o LoginController lida com as requisições relacionadas ao login.

