### Para configurar o Spring Security em uma classe no Spring Boot, você pode seguir os seguintes passos:

Crie uma classe de configuração e anote-a com `@Configuration`. Essa classe será responsável por definir a configuração do Spring Security.

Estenda a classe `WebSecurityConfigurerAdapter`, que é fornecida pelo Spring Security, para obter a configuração padrão do Spring Security e personalizá-la de acordo com suas necessidades.

Sobrescreva o método configure(HttpSecurity http) para definir as regras de segurança para diferentes URLs da sua aplicação. Por exemplo:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll() // URLs públicas que não requerem autenticação
                .antMatchers("/admin/**").hasRole("ADMIN") // URLs acessíveis apenas para usuários com papel ADMIN
                .anyRequest().authenticated() // Todas as outras URLs exigem autenticação
                .and()
            .formLogin() // Configuração do formulário de login
                .loginPage("/login") // URL da página de login personalizada
                .defaultSuccessUrl("/home") // URL de redirecionamento após o login bem-sucedido
                .and()
            .logout() // Configuração do logout
                .logoutUrl("/logout") // URL para o processo de logout
                .logoutSuccessUrl("/login?logout") // URL de redirecionamento após o logout bem-sucedido
                .and()
            .csrf().disable(); // Desabilitar proteção CSRF (somente para exemplo, não recomendado em produção)
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER") // Configuração de usuário em memória
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN"); // Configuração de admin em memória
    }
}
```

Neste exemplo, as URLs iniciadas por "`/public`" são públicas e não exigem autenticação. As URLs iniciadas por "/admin" exigem que o usuário tenha o papel "ADMIN" para acessá-las. Todas as outras URLs exigem autenticação.

A configuração configureGlobal(AuthenticationManagerBuilder auth) define os usuários e suas senhas. Neste exemplo, estamos usando autenticação em memória para fins de demonstração.

Certifique-se de personalizar essas configurações de acordo com as necessidades da sua aplicação, como autenticação baseada em banco de dados, provedores de autenticação externos, etc.

Lembre-se de adicionar as dependências do Spring Security ao seu arquivo pom.xml ou build.gradle para garantir que as bibliotecas necessárias sejam incluídas no seu projeto.

Após configurar o Spring Security dessa forma, ele estará habilitado em sua aplicação Spring Boot e as regras de segurança serão aplicadas de acordo com a configuração definida.

#
#

## Aqui está um exemplo completo de uma aplicação Spring Boot com Spring Security baseada na configuração fornecida:

### 1. Classe de configuração do Spring Security:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .and()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
            .and()
            .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Neste exemplo, definimos as configurações de autorização e autenticação. As URLs públicas estão definidas com o padrão /public/**, e elas não exigem autenticação. As URLs com o padrão /admin/** só podem ser acessadas por usuários com a função "ADMIN". Todas as outras URLs exigem autenticação.

Também configuramos o formulário de login personalizado com a página /login e o redirecionamento após o login bem-sucedido para /home. O logout é configurado com a URL /logout e o redirecionamento após o logout bem-sucedido para /login?logout.

O mecanismo de proteção CSRF está desabilitado neste exemplo, mas em um ambiente de produção, é altamente recomendável ativá-lo.

Além disso, configuramos uma autenticação em memória usando o método configure(AuthenticationManagerBuilder auth). Neste exemplo, criamos dois usuários: "user" com a função "USER" e "admin" com a função "ADMIN". As senhas são criptografadas usando o BCryptPasswordEncoder.

### 2. Controlador (controller):

```java
@Controller
public class HomeController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
```
Neste exemplo, definimos um controlador simples com quatro rotas: a página inicial ("/"), uma página pública ("/public"), uma página para administradores ("/admin") e a página de login ("/login").

3. Páginas HTML (templates):

Crie os arquivos HTML correspondentes às páginas mencionadas no controlador:

home.html
public.html
admin.html
login.html

Certifique-se de ter as dependências necessárias, como o Spring Security, Thymeleaf, etc., em seu arquivo pom.xml.

Com esse exemplo, você terá uma aplicação Spring Boot com Spring Security configurado para autorização e autenticação, e algumas páginas de exemplo para diferentes perfis de usuários.




