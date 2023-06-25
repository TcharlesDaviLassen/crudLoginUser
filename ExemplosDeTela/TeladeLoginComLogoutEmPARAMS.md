### Exemplo de como criar um gerenciamento de sessão completo e avançado usando o escopo de sessão (@SessionScope) do Spring Boot para implementar um sistema de login e logout:

### Crie uma classe UserSession anotada com @Component e @SessionScope para representar a sessão do usuário:

```java
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
private boolean loggedIn;
private String username;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```

### Crie um controlador LoginController para lidar com as operações de login e logout:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
@Autowired
private UserSession userSession;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Verificar as credenciais do usuário no banco de dados ou em outra fonte de dados
        if (username.equals("admin") && password.equals("admin123")) {
            userSession.setLoggedIn(true);
            userSession.setUsername(username);
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        userSession.setLoggedIn(false);
        userSession.setUsername(null);
        return "redirect:/login";
    }
}
```

### No seu arquivo de modelo login.html, crie um formulário de login para capturar as credenciais do usuário:

```html
<form action="/login" method="post">
    <input type="text" name="username" placeholder="Username" required><br>
    <input type="password" name="password" placeholder="Password" required><br>
    <button type="submit">Login</button>
</form>

<!-- Exibir mensagem de erro, se houver -->
<p th:if="${param.error}" class="error">Invalid username or password</p>
```

### Em outros controladores ou serviços onde você deseja verificar se o usuário está logado, injete o UserSession e verifique o estado da sessão:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
@Autowired
private UserSession userSession;

    @GetMapping("/home")
    public String home(Model model) {
        if (userSession.isLoggedIn()) {
            String username = userSession.getUsername();
            // Carregar os dados do usuário ou executar outras operações relacionadas ao usuário logado
            model.addAttribute("username", username);
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}
```

Dessa forma, você terá um sistema de login e logout com gerenciamento de sessão completo usando o escopo de sessão do Spring Boot. O objeto UserSession irá manter o estado da sessão do usuário e poderá ser injetado em outros componentes para verificar se o usuário está logado ou realizar outras operações relacionadas à sessão do usuário.