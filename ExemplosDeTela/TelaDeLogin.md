### Exemplo de código em Spring Boot que cria uma tela completa de login e utiliza as anotações @Component e @SessionScope para gerenciar a sessão do usuário:

### Crie uma classe UserSession anotada com @Component e @SessionScope para representar a sessão do usuário:

```java
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
private String username;
private boolean loggedIn;

    // Getters e Setters
}
```

### Crie um controlador LoginController para lidar com as requisições relacionadas ao login:

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
private final UserSession userSession;

    public LoginController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        if (userSession.isLoggedIn()) {
            // Redirecionar para a página de perfil do usuário se já estiver logado
            return "redirect:/profile";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Lógica de autenticação do usuário
        if (username.equals("admin") && password.equals("admin123")) {
            userSession.setUsername(username);
            userSession.setLoggedIn(true);
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        if (!userSession.isLoggedIn()) {
            // Redirecionar para a página de login se não estiver logado
            return "redirect:/login";
        }
        model.addAttribute("username", userSession.getUsername());
        return "profile";
    }
}
```

### Crie as páginas HTML correspondentes para a tela de login (login.html) e para o perfil do usuário (profile.html). Essas páginas podem ser colocadas no diretório src/main/resources/templates/.

### Exemplo de login.html:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="/login" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <button type="submit">Login</button>
        </div>
        <div th:if="${error}">
            <p th:text="${error}" style="color: red;"></p>
        </div>
    </form>
</body>
</html>
```

### Exemplo de profile.html:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <h2>Welcome, <span th:text="${username}"></span>!</h2>
    <a href="/logout">Logout</a>
</body>
</html>
```

Lembre-se de adicionar as dependências necessárias no arquivo pom.xml do seu projeto Spring Boot, como o Starter do Spring Web (spring-boot-starter-web) e o Thymeleaf (spring-boot-starter-thymeleaf).

Esse exemplo mostra como criar uma tela de login simples e gerenciar a sessão do usuário usando as anotações @Component e @SessionScope do Spring Boot. A lógica de autenticação do usuário pode ser adaptada de acordo com as necessidades do seu projeto.